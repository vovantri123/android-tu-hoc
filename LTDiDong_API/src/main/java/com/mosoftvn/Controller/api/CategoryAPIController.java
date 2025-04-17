package com.mosoftvn.Controller.api;

import com.mosoftvn.entity.Category;
import com.mosoftvn.model.Response;
import com.mosoftvn.service.ICategoryService;
import com.mosoftvn.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/category")
public class CategoryAPIController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryName") String categoryName){
//            @Validated @RequestParam("icon") MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);

        if (optCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệ thống");
        } else {
            Category category = new Category();

//            // Kiểm tra tồn tại file, lưu file
//            if (!icon.isEmpty()) {
//                UUID uuid = UUID.randomUUID();
//                String uuString = uuid.toString();
//                category.setIcon(storageService.getSorageFilename(icon, uuString));
//                storageService.store(icon, category.getIcon());
//            }

            category.setCategoryName(categoryName);
            categoryService.save(category);
            return new ResponseEntity<>(new Response(true, "Thêm Thành công", category), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateCategory")
    public ResponseEntity<?> updateCategory(
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("categoryName") String categoryName){
//            @Validated @RequestParam("icon") MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findById(categoryId);

        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else if (optCategory.isPresent()) {
//            // Kiểm tra tồn tại file, lưu file
//            if (!icon.isEmpty()) {
//                UUID uuid = UUID.randomUUID();
//                String uuString = uuid.toString();
//                optCategory.get().setIcon(storageService.getSorageFilename(icon, uuString));
//                storageService.store(icon, optCategory.get().getIcon());
//            }

            optCategory.get().setCategoryName(categoryName);
            categoryService.save(optCategory.get());
            return new ResponseEntity<>(new Response(true, "Cập nhật Thành công", optCategory.get()), HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);

        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else if (optCategory.isPresent()) {
            categoryService.delete(optCategory.get());
            return new ResponseEntity<>(new Response(true, "Xóa Thành công", optCategory.get()), HttpStatus.OK);
        }

        return null;
    }
}
