
(function ($) {
	"use strict";
					
		// Main Menu responsive
		$('.main-menu layouts').meanmenu({
			meanScreenWidth: "991",
			meanMenuContainer: '.mobile-menu',
			meanMenuOpen: '<i class="ti-align-left"></i>',
			meanMenuClose: '<i class="ti-close meanbar-close"></i>'
		}); 
		
			
		//Dropdown Menu effect
		jQuery(document).ready(function($){
		   
		   // Menu sticky
		   $(window).on('scroll',function() {    
			   var scroll = $(window).scrollTop();
			   if (scroll < 20) {
				$("#sticky-header").removeClass("sticky-menu");
			   }else{
				$("#sticky-header").addClass("sticky-menu");
			   }
			});
					
			
			// Scroll To Top
			$("#toTop").scrollToTop(600);
		});
		
		
		// News 2 
		$('.news-boxx').owlCarousel({
			loop:true,
			nav:false,
			dots:false,
			mouseDrag:true,
			autoplay:true,
			autoplayTimeout:5000,
			navSpeed:800,
			autoplaySpeed:800,
			animateIn: 'fadeIn',
			animateOut: 'fadeOut',
			margin:30,
			responsive:{
				0:{
					items:1
				},
				600:{
					items:1
				},
				1000:{
					items:2
				}
			}
		});
		
		// Client reviews 
		$('.single-review').owlCarousel({
			loop:true,
			nav:false,
			dots:true,
			mouseDrag:true,
			autoplay:true,
			autoplayTimeout:5000,
			navSpeed:800,
			autoplaySpeed:800,
			animateIn: 'fadeIn',
			animateOut: 'fadeOut',
			responsive:{
				0:{
					items:1
				},
				600:{
					items:1
				},
				1000:{
					items:1
				}
			}
		});


	// Preloader
	var overlay = document.getElementById("overlay");
		window.addEventListener('load', function(){
		  overlay.style.display = 'none';
	});
		
	//Magnific Popup Images
	$('.pop-video').magnificPopup({
		type: 'iframe',
		mainClass: 'mfp-fade',
		removalDelay: 160,
		preloader: false,
		gallery:{
		  enabled:false
		}
	});
	
	// CounterUp
	$('.counter').counterUp({
		delay: 100,
		time: 3000,
	});

	
	//Faq area Accordion
	$('.accordion > li:eq(0) a').addClass('active').next().slideDown();

    $('.accordion a').on( 'click',function(j) {
        var dropDown = $(this).closest('li').find('p');

        $(this).closest('.accordion').find('p').not(dropDown).slideUp();

        if ($(this).hasClass('active')) {
            $(this).removeClass('active');
        } else {
            $(this).closest('.accordion').find('a.active').removeClass('active');
            $(this).addClass('active');
        }

        dropDown.stop(false, true).slideToggle();

        j.preventDefault();
    });
	
	
	//Google Map
	
	function basicmap() {
        // Basic options for a simple Google Map
        // For more options see: https://developers.google.com/maps/documentation/javascript/reference#MapOptions
        var mapOptions = {
            // How zoomed in you want the map to start at (always required)
            zoom: 10,
            scrollwheel: false,
            // The latitude and longitude to center the map (always required)
            center: new google.maps.LatLng(23.871589, 90.394287), // New York
            // This is where you would paste any style found on Snazzy Maps.
            styles: [
    {
        "featureType": "water",
        "stylers": [
            {
                "visibility": "on"
            },
            {
                "color": "#b5cbe4"
            }
        ]
    },
    {
        "featureType": "landscape",
        "stylers": [
            {
                "color": "#efefef"
            }
        ]
    },
    {
        "featureType": "road.highway",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#83a5b0"
            }
        ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#bdcdd3"
            }
        ]
    },
    {
        "featureType": "road.local",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#ffffff"
            }
        ]
    },
    {
        "featureType": "poi.park",
        "elementType": "geometry",
        "stylers": [
            {
                "color": "#e3eed3"
            }
        ]
    },
    {
        "featureType": "administrative",
        "stylers": [
            {
                "visibility": "on"
            },
            {
                "lightness": 33
            }
        ]
    },
    {
        "featureType": "road"
    },
    {
        "featureType": "poi.park",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "on"
            },
            {
                "lightness": 20
            }
        ]
    },
    {},
    {
        "featureType": "road",
        "stylers": [
            {
                "lightness": 20
            }
        ]
    }
]
        };
        // Get the HTML DOM element that will contain your map 
        // We are using a div with id="map" seen below in the <body>
        var mapElement = document.getElementById('map');

        // Create the Google Map using our element and options defined above
        var map = new google.maps.Map(mapElement, mapOptions);

        // Let's also add a marker while we're at it
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(23.871589, 90.394287),
            map: map,
            title: 'Cryptox'
        });
    }
    if ($('#map').length != 0) {
        google.maps.event.addDomListener(window, 'load', basicmap);
    }
	 
}(jQuery));	




