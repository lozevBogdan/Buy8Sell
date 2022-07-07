package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.ProductDetailsViewDto;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PictureService pictureService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, PictureService pictureService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public CommentBindingDto commentBindingDto(){
        return new CommentBindingDto();
    }
    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel(){
        return new ProductAddBindingModel();
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

//    @ModelAttribute
//    public List<ProductSearchViewModel> productSearchViewModelList(){
//        return new LinkedList<>();
//    }

    @GetMapping("/all")
    public String productsPage(Model model,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel(), sellAndBuyUser.getId());

        if (!model.containsAttribute("productSearchViewModelList")){
            model.addAttribute("productSearchViewModelList",productSearchViewModelList);
        }
        return "all-products";
    }


    @GetMapping("/add")
    public String allProducts(){
        return "add-new-product";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){
        this.productService.deleteProductById(id);
        return String.format("redirect:/users/%d/products",
                sellAndBuyUser.getId());
    }


    @PostMapping("/edit/{id}")
    public String addProduct(@PathVariable Long id){
        //todo
        return "edit-product";
    }

    @GetMapping("/info/{id}")

    public String productInfo(@PathVariable Long id,Model model){
        ProductDetailsViewDto productInfoView = this.getAndIncreaseViewsProductById(id);
        model.addAttribute("productInfoView",productInfoView);
        model.addAttribute("created",productInfoView.getCreated());
        return "product-Info";
    }

    private ProductDetailsViewDto getAndIncreaseViewsProductById(Long id){
        ProductEntity currentProduct = this.productService.findById(id);
        currentProduct.setViews(currentProduct.getViews() + 1);
        currentProduct = this.productService.addProductEntity(currentProduct);
        ProductDetailsViewDto productDetailsViewDto =
                this.modelMapper.map(currentProduct, ProductDetailsViewDto.class);
        return productDetailsViewDto;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "false") boolean isPromo,
                      @Valid ProductAddBindingModel productAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){
        
        //                  todo : get id of user from Principal!!!!

        productAddBindingModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add";
        }

        ProductEntity newProduct =
                this.productService.addProductBindingModel(productAddBindingModel,sellAndBuyUser);

        return String.format("redirect:/users/%d/products",
               sellAndBuyUser.getId());
    }

    @PostMapping("/all")
    public String all(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model, @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        boolean isMinBiggerThanMax = false;

        if (productSearchingBindingModel.getMin() != null && productSearchingBindingModel.getMax() != null) {

            isMinBiggerThanMax =
                    productSearchingBindingModel.getMin() > productSearchingBindingModel.getMax();
        }

        if (bindingResult.hasErrors() || isMinBiggerThanMax) {
            redirectAttributes.addFlashAttribute("productSearchingBindingModel", productSearchingBindingModel);
            redirectAttributes.addFlashAttribute("isMinBiggerThanMax", isMinBiggerThanMax);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productSearchingBindingModel", bindingResult);

            return "redirect:/products/all";
        }

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(productSearchingBindingModel,sellAndBuyUser.getId());

        model.addAttribute("productSearchViewModelList", productSearchViewModelList);

        return "all-products";
    }



}
