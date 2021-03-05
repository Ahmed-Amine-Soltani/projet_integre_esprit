            var tpj=jQuery;
            
            var revapi471;
            tpj(document).ready(function() {
                if(tpj("#rev_slider_471_1").revolution == undefined){
                    revslider_showDoubleJqueryError("#rev_slider_471_1");
                }else{
                    revapi471 = tpj("#rev_slider_471_1").show().revolution({
                        sliderType:"standard",
jsFileLocation:"revolution/js/",
                        sliderLayout:"full",
                        dottedOverlay:"none",
                        delay:9000,
                        navigation: {
                            keyboardNavigation:"off",
                            keyboard_direction: "horizontal",
                            mouseScrollNavigation:"off",
                            mouseScrollReverse:"default",
                            onHoverStop:"off",
                            touch:{
                                touchenabled:"on",
                                swipe_threshold: 75,
                                swipe_min_touches: 1,
                                swipe_direction: "horizontal",
                                drag_block_vertical: false
                            }
                           
                        },
                        visibilityLevels:[1240,1024,778,480],
                        gridwidth:1170,
                        gridheight:790,
                        lazyType:"none",
                        shadow:0,
                        spinner:"off",
                        stopLoop:"on",
                        stopAfterLoops:0,
                        stopAtSlide:1,
                        shuffle:"off",
                        autoHeight:"off",
                        disableProgressBar:"on",
                        hideThumbsOnMobile:"off",
                        hideSliderAtLimit:0,
                        hideCaptionAtLimit:0,
                        hideAllCaptionAtLilmit:0,
                        debugMode:false,
                        fallbacks: {
                            simplifyAll:"off",
                            nextSlideOnWindowFocus:"off",
                            disableFocusListener:false,
                        }
                    });
                }
            }); /*ready*/