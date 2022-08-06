package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.OrderBYEnum;
import com.example.sellbuy.model.exception.NotAuthorizedException;
import com.example.sellbuy.model.exception.ObjectNotFoundException;
import com.example.sellbuy.model.view.productViews.ProductDetailsViewDto;
import com.example.sellbuy.model.view.productViews.ProductEditViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public CommentBindingDto commentBindingDto() {
        return new CommentBindingDto();
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel() {
        return new ProductAddBindingModel();
    }

    @ModelAttribute
    public MessageBindingModel messageBindingModel() {
        return new MessageBindingModel();
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel() {
        return new ProductSearchingBindingModel();
    }

    @GetMapping("/all")
    public String productsPage(Model model,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel(),
                        sellAndBuyUser != null ? sellAndBuyUser.getId() : null, false);

        if (!model.containsAttribute("productSearchViewModelList")) {
            model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        }

        return sellAndBuyUser != null ? "products-all" : "products-all-anonymous";
    }

    @GetMapping("/all/promotion")
    public String allPromotions(Model model,
                                @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel().setOrderBy(OrderBYEnum.NEWEST),
                        sellAndBuyUser != null ? sellAndBuyUser.getId() : null,
                        true);
        if (!model.containsAttribute("productSearchViewModelList")) {
            model.addAttribute("productSearchViewModelList", productSearchViewModelList);

        }
        return sellAndBuyUser != null ? "products-promotions" : "products-promotions-anonymous";
    }

    @PostMapping("/all/promotion")
    public String allPromotions(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model, @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                                @RequestParam(value = "category", required = false) String category) {

        boolean isMinBiggerThanMax = false;

        if (category != "" && category != null ) {
            productSearchingBindingModel.setCategory(CategoryEnum.valueOf(category));
        }

        if (productSearchingBindingModel.getMin() != null && productSearchingBindingModel.getMax() != null) {
            isMinBiggerThanMax =
                    productSearchingBindingModel.getMin() > productSearchingBindingModel.getMax();
        }

        if (bindingResult.hasErrors() || isMinBiggerThanMax) {
            redirectAttributes.addFlashAttribute("productSearchingBindingModel", productSearchingBindingModel);
            redirectAttributes.addFlashAttribute("isMinBiggerThanMax", isMinBiggerThanMax);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productSearchingBindingModel", bindingResult);

            return "redirect:/products/all/promotion";
        }
        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(productSearchingBindingModel,
                        sellAndBuyUser != null ? sellAndBuyUser.getId() : null,
                        true);
        model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        model.addAttribute("noResults", productSearchViewModelList.size() == 0);

        return sellAndBuyUser != null ? "products-promotions" : "products-promotions-anonymous";
    }

    @GetMapping("/add")
    public String allProducts() {
        return "product-add";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {
        this.productService.deleteProductById(id);
        return "redirect:/users/products";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {

        if(!this.productService.isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(
                id, sellAndBuyUser.getId()
        )){
            throw new NotAuthorizedException();
        }

        ProductEditViewModel productEditViewModel =
                this.productService.findByIdProductSearchAndEditViewModel(id);
        if (!model.containsAttribute("productEditViewModel")) {
            model.addAttribute("productEditViewModel", productEditViewModel);
        }
        return "product-edit";
    }


    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @RequestParam(defaultValue = "false") boolean isPromo,
                              @Valid ProductEditViewModel productEditViewModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,@AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {
        if(!this.productService.isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(id, sellAndBuyUser.getId())){
            throw new NotAuthorizedException();
        }

        productEditViewModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productEditViewModel", productEditViewModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productEditViewModel",
                    bindingResult);
            return "redirect:/products/edit/" + id;
        }
        ProductEntity updatedProduct =
                this.productService.updateProductById(id, productEditViewModel);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/products/info/" + id;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({NotAuthorizedException.class})
    public String notAuthorized () {

        return "not-authorized";

    }

    @GetMapping("/info/{id}")
    public String productInfo(@PathVariable Long id, Model model,
                              @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {

        ProductDetailsViewDto productInfoView = this.productService.getAndIncreaseViewsProductById(id);

        if(productInfoView == null){
            throw new ObjectNotFoundException(id,"Product");
        }

        if (sellAndBuyUser != null) {
            if (productService.isConsist(this.userService.findById(sellAndBuyUser.getId()).
                    getFavoriteProducts(), productInfoView)) {
                productInfoView.setProductIsFavorInCurrentUser(true);
            }
        }

        model.addAttribute("productInfoView", productInfoView);

        return sellAndBuyUser != null ? "product-Info" : "product-Info-anonymous";
    }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler({ObjectNotFoundException.class})
  public ModelAndView onProductNotFound(ObjectNotFoundException onfe) {
    ModelAndView modelAndView = new ModelAndView("object-not-found");
    modelAndView.addObject("objectId", onfe.getObjectId());
    modelAndView.addObject("typeOfObject", onfe.getTypeOfObject());
    return modelAndView;

  }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "false") boolean isPromo,
                      @Valid ProductAddBindingModel productAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {

        productAddBindingModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add";
        }

        ProductEntity newProduct =
                this.productService.addProductBindingModel(productAddBindingModel, sellAndBuyUser);

        return "redirect:/users/products";
    }

    @PostMapping("/all")
    public String all(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model, @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                      @RequestParam(value = "category", required = false) String category) {

        boolean isMinBiggerThanMax = false;

        if (category != "" && category != null) {
            productSearchingBindingModel.setCategory(CategoryEnum.valueOf(category));
        }

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
                this.productService.filterBy(productSearchingBindingModel,
                        sellAndBuyUser != null ? sellAndBuyUser.getId() : null,
                        false);
        model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        model.addAttribute("noResults", productSearchViewModelList.size() == 0);

        return sellAndBuyUser != null ? "products-all" : "products-all-anonymous";
    }
}
