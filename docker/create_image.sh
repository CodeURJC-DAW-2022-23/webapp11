#!/bin/bash

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Docker could not be found, would you like to install it? [Y/n]"
    read -r answer
    if [[ "$answer" == "n" ]]; then
        exit 1
    fi
    echo "Installing Docker..."
    # Check the package manager
    if command -v apt-get &> /dev/null; then
        sudo apt-get install docker.io
    elif command -v pacman &> /dev/null; then
        sudo pacman -S docker
    elif command -v dnf &> /dev/null; then
        sudo dnf install docker
    elif command -v yum &> /dev/null; then
        sudo yum install docker
    elif command -v brew &> /dev/null; then
        brew install docker
    else
        echo "Your package manager is not supported, please install Docker manually"
        exit 2
    fi
    exit 0
fi

# Name of the image to create
# shellcheck disable=SC2034
IMAGE_NAME="4rius/threetechmarket"

# Docker's image version
IMAGE_VERSION="1.0.0"

# Dockerfile to use
DOCKERFILE="Dockerfile"

# The Dockerfile's directory (docker/)
DOCKERFILE_DIR="docker"
ANGULAR_DIR="frontend/3techmarket"

# Set the current directory to the root of the project because Docker will set
# the context to the directory where we run the command
# shellcheck disable=SC2164
cd "$(dirname "$0")/.." || exit 1

# Use dockerized Node.js to run npm install and ng build, rm the container when it's done using node:lts-slim
docker run --rm -v "$(pwd)/${ANGULAR_DIR}:/app" -w /app node:lts-slim npm install
docker run --rm -v "$(pwd)/${ANGULAR_DIR}:/app" -w /app node:lts-slim npm run build --configuration=production

# Copy the result of the build to the Spring Boot project
cp -r "${ANGULAR_DIR}/dist/3techmarket" "src/main/resources/static/spa"

# Build the image
docker build -t "${IMAGE_NAME}:${IMAGE_VERSION}" -f "${DOCKERFILE_DIR}/${DOCKERFILE}" .

# Tag the image
docker tag "${IMAGE_NAME}:${IMAGE_VERSION}" "${IMAGE_NAME}:latest"

# Fancy script
echo "Would you like to test the image (needs docker-compose)? [Y/n]"
read -r answer
if [[ "$answer" == "n" ]]; then
    echo "Image created successfully"
    echo "Image name: ${IMAGE_NAME}"
    echo "Image version: ${IMAGE_VERSION}"
    exit 0
fi
echo "The containers will be automatically removed if you stop the script"
echo -n "Dockering up"
for i in {1..5}; do
    echo -n "."
    sleep 0.5
done
echo
# Change the directory to the docker-compose directory
cd docker || exit 1
docker-compose up
docker rm $(docker ps -aq)


# When we finish testing, we can push the image to Docker Hub
# docker push "${IMAGE_NAME}:${IMAGE_VERSION}"