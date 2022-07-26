package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.repository.PictureRepository;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ProductService productService;

    public PictureServiceImpl(PictureRepository pictureRepository, @Lazy ProductService productService) {
        this.pictureRepository = pictureRepository;
        this.productService = productService;
    }

    @Override
    public void initializePictures() {

//        if(pictureRepository.count() == 0){
//            PictureEntity picture1 = new PictureEntity();
//            picture1.setUrl("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHcAAAB3CAMAAAAO5y+4AAAAh1BMVEX///8BZrFvb28AW62lu9lsbGxmZmZgYGBpaWljY2P4+Phzc3OHh4d9fX3g4OC5ubnp6enY2NhbW1unp6fR0dHGxsaNjY0AYa/v7++ZmZmwsLCfn5/z9vrf5/G3yuKQr9SAo81qlMiAlbHI2OoAU6pAZI6Xrs5HgL3T3uxUhb96ncu/0eYnb7XDM92qAAAJH0lEQVRogcVbaZ+bLBBnTRG8jbdmu0eb7tGn3//zPTNgosYBzCb9dV60m0T4O/cAA2PXUrevs7KPkyjyoiiJ+zKr993Vs1wHWQ0x931fSs49TZxLid/EQ/WXwNMMMSe8kU6f4ac4S+8Nmg+J1JjAYBD4XpLESEnC/SAY2ee+TIb8fqBh1WhQgORJU9Zp3oUn6vK0LpuEA7iGbqrwPqhZIsYpk7LK6UnDvCpHiXCRZLcjh1kU4GxSurUHFiAlIgfRrch15CtW+UxvITjSMOi/qyGr54acD1wx7Uf1DahpEmjBVfNvOxH4stDMR+BERbYYVGm1BMmXjftQcESNLybIlQgO+OdeIG/Zxbg0RmReHL6Eule6WqECLgrBC1CDvVL9sBoLyMom9tfDlmhOflStf8lhzkRx2fkeTyjc0TB4UF6Jmsd6GGWWqfB4Bo4cssGXZcIlOXmoXzy+Ko7sI5CxjGgxAa7fRVzsGUSLPObSoEfrJCTVGHv83uCDYEx+WPvysA94zGLOTfYT9ig0udmjsgAfv7TSM1UB4DLOE8gTFWs4780zIQOBcaYllWA33DOLB3DBmAcJKSJCk6ZxOzXBHmbyBGV4JKxMLMm0BlyYF5Tngwh7yRvqqV6Hky6B58QGsx4EWqHticz3fDVxIcECShq3Fic09Aw3xxnCqomyqEhIYQMu8MvCNEWhAG68NsAuArDRhRoEdugYZKhhuxjcD31lTYPPg8UnAvcgPf/MIwIHVqveo8ehkFEtQUSLsOTx7Osaao4VboWBbPoY47QWP84jrp8HWB7tw14mxFNdt8QJVzYYJtyT87gOn3lkjlzwXtyDWcIYYGFc7Sd93F+fz0pfSRkqlXFsB/NKo7HC41ocAOul6jWhuJHBCvjx+9Pzy+vrj/5QZkTlkwYeBDJdqYxjUYG+wZv2wZhLIbwp+AbqxEOiosOMfr58tsfdrm3bX34gCvCmplzqDqTqpYDqeROTGdoWrWJgUmLoGQJPYPo7QDxMWQhOMCnw46U9tu2Dot1bnlZ11stCFLychIIQMaBijBTnPNqDDiUFC6bPI5BZVWijh7iFHphLT5weefpzHDEV7rfx664+BEVxKoa6QhXyUhwgvSh298hNCDZLZa600ILA7BqPXCMPkOfG+Pv0sHuY0xkXqW4KoYuEvSpyRN+xSLEbHoQKV6jGYm2joBSl+FSLOxthGwgASswf70vUC1wY2BdCZfk88kWfYwxCBirMwgW+Ubl06vF94W0iZZk52jy+BVoBmJj6nz237YMdV1VVKnt2B8UWaBdCAJS1Y90SRuuwhd+dbKDDN/Q8ZVq+yjns8fOSWQoX3p6L5uRWUArFitlzRQu5YuTtTGCD/OzXKndhJixH2I+HFbM0LssbEaVnThJgVvIpJcT8ouhV7E467zBwJR2YlnrsP4JZAy66gVZMFuglYjMLj+klw1kwY5epQAlO5esSxQBrwIVMqhTWoLIkX+oTGJ5XPRjHxdLEMXd5wWCBNeGCGjF9gr9ycbiIoeiks+xVibWF91DNoJt/mGCNuAAMcY7lhUd66xTCoCb0/NXC4FBgvHg0oVpwUW3AFJXHMDGfkzeEQo8To/Gfd8qSXbhgXGStBwSxTJ4sbfDpNQ7Qs1HKVlzWmAqqYVYAYXVAFwMfZm7tuDk3zJjLsymlculEM7JI2Y4LtmWYEkserXmsh2mpPFmk7MBlcUAsYRdo8UURNpEN1YWbFhH9/Um6HSfSkyI7uw5c1guaYRQ0ZlZwKXrlzP7cwi8wTHNTSh0swItoVXwcb8JlDVFdML2MRU8C9XLS5n/bjHkDbk0vA3OuFSwXoXpGDlgnLhNEEBwXE3pPhgxqPx1iduP2tKAxG3RsbzKrl5v5rellLxrWHtZAhjXi5824XUEKEkpIqJ4yWHBS4nh0wbpxmZTk/hcsJTLFNWXO313q3YDbF9TMudIsbjFSmyiOYLUJNyuowIABssd9L9KNbJl3K25dUPkmTNCBE4P7Os15A25FRg504AQWT9S+CGOvd8BNA2oLMYQIGcEqxoS7c9DRiZv71FbehEuN+fHr7dut9PbrBzU14HpGfnvSuxb07egSSftq5Nek3wMZTZa4TpO34JrsuTSUC9fhvlC4yp5N/psVzv1qN+7umcRFk+rHcueSKtLnr8V9IobpeGWKz3lh3kbfjHv8Ts2s4rMpH4WS3G26Drd9JIbpfGTMv03hOsB24raf1DCdf431xiBchuXGfaGG6XrDWF+lTgU7cY8/qWG6vjLXk1xQ316D21KjxnrSXD+XLkG7cNvf1KhT/WxcL6R0WbYd9/hBjTqtF8zro4Sufzfj/iFHndZH5vVgJeyW5cAlg9W0HrSsfyM7wy5+yUHT7oJ5vV8F1oMzO66B3Qkt5cb9jdhq0lbc9p0eNO1vWPZzckm72BZc0pjn+zm2/avMuAHmwCUzL1vuXxn26xQ1ljNUCy6dEdhyv47en9QURuajTBu/VAJkF/uT5H7siVLfaFtm3B2t3Mv9WGL/eaK9MAEbcXf/mZhY7j+v9tsXVAmDqE24RtjL/fbL84UL2vu0cRlwzbCr84Xlecr6+Ug0hB+TuO2DSbfEeYpm2Bybwkbw9a8U7u7TYMmMPD+anZfRlMl1Z9Iat20N4UK9PHFeNp0PmihvguLi6HuFu3s3y9hwPng+DzVTFYmimb/vBe7ugc5AIxnOQ8/nvzbkpBCir0919Ry3Pf6xohrPfxnzxvNuG6UlL0Qh+6yu0vxtd9Lqsf1tkzASnnd75C/n830H9NDIoihE4P9q23a3O7afL2SdvCA8LjSp8dzP4KIwr7Ly0P94fX15fvpudpyJbP0MU//G3amLbP0bU7/KvcnRrzL159yXXP05s36ke5K7H2nef3VXWFf/1ZZ+sytpW7/Zhv66q2hzf53m2NZPeA1d0U/o7J+8gq7qnxz7RQNTv+hmurZf9CutrXeaxNYPvI2+1A/Mzv3PX21T/2L/M0NTNPR7b6Cx3/uLTmHob9+CekN/OzP18zvo9n5+Rt9fsNF97i+w+X2NZMN9jeRe9zXYv7qfoib9J/dxFOVDwuf3jzh9/4jf9f6RpuV9K2953epv3bfS9E/ul53B73Kf7n851YmSk5E60gAAAABJRU5ErkJggg==");
//            picture1.setUrl("");
//            pictureRepository.save(picture1);
//        }
    }

    @Override
    public PictureEntity addPictureEntity(PictureEntity picture1) {
        return pictureRepository.save(picture1);
    }

    @Override
    public PictureEntity getFirstPicture() {
        return pictureRepository.findAll().get(0);
    }

    @Override
    public PictureEntity addPictureInDb(PictureEntity pictureEntity) {

        return this.pictureRepository.save(pictureEntity);
    }


    @Override
    public void deleteByProductId(Long id) {
        ProductEntity productById = this.productService.findById(id);
        PictureEntity pictureByProductPictureId = this.pictureRepository.findById(productById.getPicture().getId()).get();

        System.out.println();

        pictureByProductPictureId.setProduct(null);
        pictureByProductPictureId = this.pictureRepository.save(pictureByProductPictureId);

        this.pictureRepository.deleteById(pictureByProductPictureId.getId());
        System.out.println();
    }

    @Override
    public PictureEntity findByProductId(Long id) {
       // return this.pictureRepository.findByProductId(id);
        return null;
    }

    @Override
    public Optional<PictureEntity> findByUrl(String urlPicture) {

       return this.pictureRepository.findByUrl(urlPicture);
    }

    @Override
    public void deleteOldPictureById(Long id) {
        this.pictureRepository.deleteById(id);
    }

}