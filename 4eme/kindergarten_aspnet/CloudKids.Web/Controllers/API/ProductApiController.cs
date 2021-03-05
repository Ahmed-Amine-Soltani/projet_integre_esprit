using CloudKids.Data;
using CloudKids.Domain.Entities;
using CloudKids.Service;
using CloudKids.Web.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Http;


namespace CloudKids.Web.Controllers.API
{



    [RoutePrefix("Product/Api")]
    public class ProductApiController : ApiController
    {
        IProductService MyProductService;
        ICartLineService MyCartLineService;
        ICartService MyCartService;
        IPointsLoyaltyService MyPointsLoyaltyService;

        public ProductApiController()
        {
            MyProductService = new ProductService();
            MyCartLineService = new CartLineService();
            MyCartService = new CartService();
            MyPointsLoyaltyService = new PointsLoyaltyService();

        }

        [Route("GetAllProduct")]
        public IHttpActionResult GetAllProductWebService()
        {
            //var WS_productList = new List<ComplexProductObject>();
            //var productList = MyProductService.GetAllProduct();
            //foreach (var product in productList)
            //{
            //    ComplexProductObject WS_product = new ComplexProductObject();

            //    WS_product.ProductName = product.Name;
            //    WS_product.Img1Base64 = Convert.ToBase64String(product.Img1);

            //    // WS_product.Img1 = product.Img1;
            //    WS_productList.Add(WS_product);

            //}
            //var jsonResult = Json(WS_productList.ToArray(), JsonRequestBehavior.AllowGet);
            //jsonResult.MaxJsonLength = int.MaxValue;
            List<Product> productList = MyProductService.GetAllProduct();
            return Ok(productList);
        }

        [Route("GetCartLinesByCartId/{cartId}")]
        public IHttpActionResult GetCartLinesByCartIdWebService(int cartId)
        {
            //var WS_CartLineList = new List<ComplexProductObject>();
            var cartLineList = MyCartLineService.GetCartLinesByCartId(cartId);
            //foreach (var cartLine in cartLineList)
            //{
            //    ComplexProductObject WS_cartLine = new ComplexProductObject();

            //    WS_cartLine.CartLineId = cartLine.Id;

            //    //WS_product.ImgBase64 = Convert.ToBase64String(product.Img1);

            //    // WS_product.Img1 = product.Img1;
            //    WS_CartLineList.Add(WS_cartLine);

            //}
            //var jsonResult = Json(WS_productList.ToArray(), JsonRequestBehavior.AllowGet);
            //jsonResult.MaxJsonLength = int.MaxValue;
            return Ok(cartLineList);
        }


        [Route("GetNotYetPurchasedCartByParentId/{parentId}")]
        public IHttpActionResult GetNotYetPurchasedCartByParentIdWebService(int parentId)
        {

            var cart = MyCartService.GetNotYetPurchasedCartByParentId(parentId);
            //if(cart == null)
            //{
            //    return Ok("not found");
            //}  
            return Ok(cart);
        }


        [Route("GetProductById/{productId}")]
        public IHttpActionResult GetProductByCartLineIdWebService(int productId)
        {
            var product = MyProductService.GetById(productId);
            return Ok(product);
        }


        [Route("GetPointsLoyalty/{parentId}")]
        public IHttpActionResult GetPointsLoyalty(int parentId)
        {
            var pointsLoyalty = MyPointsLoyaltyService.GetPointsLoyalty(parentId);
            return Ok(pointsLoyalty);
        }



        [Route("GetAllCartByParentId/{parentId}")]
        public IHttpActionResult GetAllCartByParentIdWebService(int parentId)
        {
            // her i retrieve all parent cart 
            var cartList = MyCartService.GetAllCartByParentId(parentId);
            //var carttest = MyCartService.GetAll();


            //List<CartLine> cartLineList = MyCartLineService.GetCartLinesByCartId(10);
            // here i use parent cart.id to retrieve cartLines
            //foreach (Cart cart in cartList)
            //{
            //    List<CartLine> cartLineList = MyCartLineService.GetCartLinesByCartId(cart.Id);


            //}

            return Ok(cartList);
        }


        CloudKidsContext db = new CloudKidsContext();

        [Route("AddProductToCart/{userId}/{productId}/{quantityChoosed}")]
        public IHttpActionResult AddProductToCartWebService(int userId, int productId, int quantityChoosed)
        {
            try
            {
                Cart testCart = MyCartService.GetCartByParentId(userId);
                Product product = MyProductService.GetById(productId);
                if (testCart == null)
                {
                    //  why i use db.Carts.Create()  https://stackoverflow.com/questions/6220063/ef-code-first-not-returning-object-as-dynamicproxies-until-after-the-http-reques
                    Cart newCart = db.Carts.Create();
                    newCart.CartStatus = true;
                    newCart.ParentId = userId;
                    newCart.PurchaseDate = DateTime.UtcNow;
                    MyCartService.Add(newCart);
                    MyCartService.Commit();

                    CartLine cartLine = db.CartLines.Create();
                    cartLine.DateAddedToCart = DateTime.UtcNow;
                    cartLine.CartId = newCart.Id;
                    cartLine.ProductId = productId;
                    cartLine.QuantityChoosed = quantityChoosed;
                    cartLine.TotalCartLinePrice = product.Price * quantityChoosed;
                    cartLine.ProductPrice = product.Price;
                    MyCartLineService.Add(cartLine);
                    MyCartLineService.Commit();


                    newCart.TotalCartPrice += (cartLine.ProductPrice * quantityChoosed);
                    MyCartService.Commit();


                }
                else
                {

                    CartLine cartLine = db.CartLines.Create();
                    cartLine.DateAddedToCart = DateTime.UtcNow;
                    cartLine.CartId = testCart.Id;
                    cartLine.ProductId = productId;
                    cartLine.QuantityChoosed = quantityChoosed;
                    cartLine.TotalCartLinePrice = product.Price * quantityChoosed;
                    cartLine.ProductPrice = product.Price;
                    MyCartLineService.Add(cartLine);
                    MyCartLineService.Commit();



                    testCart.TotalCartPrice += (cartLine.ProductPrice * quantityChoosed);
                    MyCartService.Commit();



                }
            }

            catch (IOException e)
            {
                throw e;

            }

            return Ok("product added to cart");
        }

        [Route("ConfirmPurchase/{parentId}")]
        public IHttpActionResult ConfirmPurchaseWebService(int parentId)
        {
            Cart myCart = MyCartService.GetCartByParentId(parentId);
            ICollection<CartLine> cartLines = MyCartLineService.GetCartLinesByCartId(myCart.Id);
            double totalPrice = 0;
            foreach (var cartLine in cartLines)
            {
                totalPrice += (cartLine.ProductPrice * cartLine.QuantityChoosed);
            }
            myCart.TotalCartPrice = totalPrice;
            myCart.CartStatus = false;
            //myCart.CartLines = cartLines;
            MyCartService.Commit();



            // ------------ PointsLoyalty ---------------/
            //PointsLoyalty pointsLoyalty = MyPointsLoyaltyService.GetPointsLoyalty(parentId);
            //if (pointsLoyalty == null)
            //{
            //    PointsLoyalty pointsLoyaltyNew = new PointsLoyalty();
            //    pointsLoyaltyNew.ParentId = parentId;
            //    pointsLoyaltyNew.Points = Convert.ToInt32(totalPrice / 3);
            //    MyPointsLoyaltyService.Add(pointsLoyaltyNew);
            //    MyPointsLoyaltyService.Commit();
            //}
            ////else
            ////{
            ////    pointsLoyalty.Points += Convert.ToInt32(totalPrice / 3);
            ////    MyPointsLoyaltyService.Commit();
            ////}

            return Ok("cart purchased");

        }


        [Route("ConfirmPurchaseUsingLoyaltyPoints/{parentId}/{amountToPay}")]
        public IHttpActionResult ConfirmPurchaseUsingLoyaltyPoints(int parentId ,int amountToPay)
        {
            Cart myCart = MyCartService.GetCartByParentId(parentId);
            myCart.TotalCartPrice = amountToPay;
            myCart.CartStatus = false;
            MyCartService.Commit();
            return Ok("cart purchased");
        }



        [Route("UpdatePointsLoyalty")]
        public IHttpActionResult Put(PointsLoyalty pointsLoyalty)
        {

            PointsLoyalty pointsLoyaltyTest = MyPointsLoyaltyService.GetById(pointsLoyalty.Id);
            if (pointsLoyaltyTest == null)
            {
                MyPointsLoyaltyService.Add(pointsLoyaltyTest);
                MyPointsLoyaltyService.Commit();
            }

            pointsLoyaltyTest.Points = pointsLoyalty.Points;
            MyPointsLoyaltyService.UpdatePointsLoyalties(pointsLoyalty.Points,pointsLoyalty.ParentId);
            MyPointsLoyaltyService.Commit();
            return Ok();
        }



        [Route("InsertPointsLoyalty/{parentId}/{points}")]
        public IHttpActionResult InsertLoyaltyPoint(int parentId , int points)
        {
            PointsLoyalty pointsLoyaltyTest = MyPointsLoyaltyService.GetById(parentId);
            pointsLoyaltyTest.Points = points;
            MyPointsLoyaltyService.Update(pointsLoyaltyTest);
            MyPointsLoyaltyService.Commit();
            return Ok();
        }


        [Route("CreatePointsLoyalty/{parentId}")]
        public IHttpActionResult CreatePointsLoyalty(int parentId)
        {
            if(parentId != 0)
            {
            PointsLoyalty pointsLoyaltyNew = new PointsLoyalty();
            pointsLoyaltyNew.ParentId = parentId;
            pointsLoyaltyNew.Points = 0;
            MyPointsLoyaltyService.Add(pointsLoyaltyNew);
            MyPointsLoyaltyService.Commit();
            return Ok("created");

            }
            return Ok("not created");
        }


        [Route("TestCreationPointsLoyalty/{parentId}")]
        public IHttpActionResult TestCreationPointsLoyalty(int parentId)
        {
            var loyaltyPoints = MyPointsLoyaltyService.GetLoyaltyPointsByParentId(parentId);
            if (loyaltyPoints == null)
            {
                return Ok("no");
            }
            else
            {
                return Ok("yes");
            }
        }




        //[Route("PaymentWithPaypal")]
        //public IHttpActionResult test()
        //{
        //    ProductController pc = new ProductController();
        //    pc.PaymentWithPaypal();


        //    return Ok();
        //}

    }
}