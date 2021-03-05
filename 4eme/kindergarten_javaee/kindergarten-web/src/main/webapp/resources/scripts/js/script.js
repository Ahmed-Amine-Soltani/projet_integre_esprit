$(window).on("load", function() {
    "use strict";


    //  ============= MOBILE RESPONSIVE MENU ===============

    $(".menu-btn").on("click", function(){
      $(this).toggleClass("active");
      $(".responsive-mobile-menu").toggleClass("active");
    });

    $(".responsive-mobile-menu ul ul").parent().addClass("menu-item-has-children");
    $(".responsive-mobile-menu ul li.menu-item-has-children > a").on("click", function() {
      $(this).parent().toggleClass("active").siblings().removeClass("active");
      $(this).next("ul").slideToggle();
      $(this).parent().siblings().find("ul").slideUp();
      return false;
    });

    // ===================================== STICKY HEADER =========================================//

    if ($(window).width() > 991) {
      $(window).on("scroll", function() {
          if ($(this).scrollTop() > 1){  
           $('header.pb').addClass("sticky");
        }
        else{
           $('header.pb').removeClass("sticky");
        }
      });
    };


    // ============== Custom Tabs Function ============= 

    $('.prod-tab-list ul li').on("click", function(){
        var tab_id = $(this).attr('data-tab');
        $('.prod-tab-list ul li').removeClass('current');
        $('.prod-tab-details').removeClass('current');
        $(this).addClass('current animated fadeIn');
        $("#"+tab_id).addClass('current animated fadeIn');
    });

    //  ============ PROGRESS BAR ANIMATION =========

    $('.progress .progress-bar').css("width",
        function() {
            return $(this).attr("aria-valuenow") + "%";
        }
    );

    /*=================== Accordion ===================*/

    $(".toggle").each(function(){
        $(this).find('.content').hide();
        $(this).find('h2:first').addClass('active').next().slideDown(500).parent().addClass("activate");
        $('h2', this).on("click", function() {
            if ($(this).next().is(':hidden')) {
                $(this).parent().parent().find("h2").removeClass('active').next().slideUp(500).removeClass('animated fadeInUp').parent().removeClass("activate");
                $(this).toggleClass('active').next().slideDown(500).addClass('animated fadeInUp').parent().toggleClass("activate");
            }
        });
    });

    //  ============== Login Div Open ============== 

    $(".login-dv-open").on("click", function(){
        $("#login-div").slideToggle();
        return false;
    });

    $(".coupan-dv-open").on("click", function(){
        $("#coupan-dv").slideToggle();
        return false;
    });


    //  ============== Create Account ================

      $('#c2').on("click", function(){
          if (this.checked) {
              $('.cr-info').slideToggle();
          };
      });



    // ==================== Gaping Functions =============== 

    var gap = $(".container").offset().left;
    $(".overview-info, .cms-opn-info.pd-left").css({
        "padding-left": gap
    });

    var gap2 = $(".container").offset().left;
    $(".overview-progress, .cms-opn-info.pd-right").css({
        "padding-right": gap2
    });


    var overview_info_height = $(".overview-info").innerHeight();
    $(".class-overview-img").css({
        "height": overview_info_height
    });

    var overview_progress = $(".overview-progress").innerHeight();
    $(".class-overview-img.st2").css({
        "height": overview_progress
    });


    // =================== Scroll to Top Button ================== 


     //Check to see if the window is top if not then display button
    
    var header_height = $("header").innerHeight();
    $(window).on("scroll", function(){
        if ($(this).scrollTop() > header_height) {
            $('.scrollTop').addClass("show");
        } else {
            $('.scrollTop').removeClass("show");
        }
    });

    $(".scrollTop").css({
      "right": gap
    });

    $('.scrollTop').on("click", function(){
        $('html, body').animate({scrollTop : 0},1000);
        return false;
    });

    // =================== ROOM SLIDER ===============

    $('.room-slider').slick({
        slidesToShow: 1,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        dots: false,
        autoplaySpeed: 2000
    });


    // =================== BLOG POST CAROUSEL ===============

    $('.blog-post-carousel').slick({
        slidesToShow: 3,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        dots: false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]
    });


    //  =============== PRODUCT CAROUSEL =============

    $('.product-carousel').slick({
        slidesToShow: 4,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        dots: false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]
    });

    // =================== STAFF SLIDER ===============

    $('.staff-carousel').slick({
        slidesToShow: 4,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        dots: false,
        arrows:false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 4,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]
    });

    // =================== STAFF2 SLIDER ===============

    $('.staff-carousel2').slick({
        slidesToShow: 3,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        prevArrow:'<span class="slick-previous"></span>',
        nextArrow:'<span class="slick-nexti"></span>',
        dots: false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]
    });

    // =================== BLOG POST CAROUSEL 2 SLIDER ===============

    $('.blog-post-carousel2').slick({
        slidesToShow: 2,
        slck:true,
        slidesToScroll: 1,
        autoplay: false,
        dots: false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]
    });

    // =================== POST GALLERY SLIDER ===============

    $('.post-gallery').slick({
        slidesToShow: 1,
        slck:true,
        slidesToScroll: 1,
        prevArrow:'<span class="slick-previous"></span>',
        nextArrow:'<span class="slick-nexti"></span>',
        autoplay: true,
        dots: false,
        autoplaySpeed: 2000
    });


    // =================== TESTIMONIAL SLIDER ===============

    $('.testimonial-carousel').slick({
        slidesToShow: 1,
        slck:true,
        slidesToScroll: 1,
        prevArrow:'<span class="slick-previous"></span>',
        nextArrow:'<span class="slick-nexti"></span>',
        autoplay: true,
        dots: false,
        autoplaySpeed: 2000
    });


});


