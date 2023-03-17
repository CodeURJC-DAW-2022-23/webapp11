#!/bin/bash

# Name of the image to create
# shellcheck disable=SC2034
IMAGE_NAME="sdaw/threetechmarket"

# Docker's image version
IMAGE_VERSION="1.0.0"

# Dockerfile to use
DOCKERFILE="Dockerfile"

# The Dockerfile's directory (docker/)
DOCKERFILE_DIR="docker"

# Set the current directory to the root of the project because Docker will set
# the context to the directory where we run the command
# shellcheck disable=SC2164
cd "$(dirname "$0")/.." || exit 1

# Build the image
docker build -t "${IMAGE_NAME}:${IMAGE_VERSION}" -f "${DOCKERFILE_DIR}/${DOCKERFILE}" .

# Tag the image
docker tag "${IMAGE_NAME}:${IMAGE_VERSION}" "${IMAGE_NAME}:latest"