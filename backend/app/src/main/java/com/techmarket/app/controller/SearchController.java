package com.techmarket.app.controller; // This doesn't match the file path for some reason

@Controller
public class SearchController {
    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam("product") String product,
                       @RequestParam(value = "start", required = false, defaultValue = "0") int start) {
    List<Product> products = productService.searchProducts(product);
    int end = Math.min(start + 5, products.size());  // Get 5 results per row
    List<Product> results = products.subList(start, end);  // Get results for the second row, sublist the list of 10 results provided by the search algorithm
    if (results.isEmpty()) {
      model.addAttribute("results", null);
    } else {
      model.addAttribute("results", results);
    }
    model.addAttribute("product", product);  // Search query
    model.addAttribute("start", end);  // Start index for next page
    model.addAttribute("hasMore", end < products.size());  // Boolean to check if there are more results
    return "searchtemplate";  // Return search.html
  }
}
