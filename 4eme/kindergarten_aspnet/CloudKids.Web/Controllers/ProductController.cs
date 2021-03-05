using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Microsoft.AspNet.Identity;
using CloudKids.Domain.Entities;
using CloudKids.Web.Models;
using Microsoft.AspNet.Identity.Owin;
using CloudKids.Service;
using System.IO;
using System.Text;
using System.Net;
using CloudKids.Data;
using System.Data.Entity;
using Amazon;
using Amazon.Runtime;
using Amazon.S3;
using Amazon.S3.Model;
using Amazon.S3.Transfer;
using System.Configuration;
using PayPal.Api;
using Amazon.Polly;
using Amazon.Polly.Model;
using NAudio.Wave;
using NAudio.Wave.SampleProviders;
using System.Windows.Forms;


namespace CloudKids.Web.Controllers
{

    public class ProductController : Controller
    {
        CloudKidsContext db = new CloudKidsContext();
        IProductService MyProductService;
        IProductCategoryService MyProductCategoryService;
        ICartLineService MyCartLineService;
        ICartService MyCartService;
        IPointsLoyaltyService MyPointsLoyaltyService;



        ApplicationUser user;


        // upload on s3 variables
        private const string keyName = "updatedtestfile.txt";
        private const string filePath = null;
        // Specify your bucket region (an example region is shown).  
        private static readonly string bucketName = ConfigurationManager.AppSettings["BucketName"];
        private static readonly RegionEndpoint bucketRegion = RegionEndpoint.EUWest1;
        private static readonly string accesskey = ConfigurationManager.AppSettings["AWSAccessKey"];
        private static readonly string secretkey = ConfigurationManager.AppSettings["AWSSecretKey"];



        public ProductController()
        {
            MyProductService = new ProductService();
            MyProductCategoryService = new ProductCategoryService();
            MyCartLineService = new CartLineService();
            MyCartService = new CartService();
            MyPointsLoyaltyService = new PointsLoyaltyService();

            user = System.Web.HttpContext.Current.GetOwinContext().GetUserManager<ApplicationUserManager>()
                 .FindById(System.Web.HttpContext.Current.User.Identity.GetUserId<int>());


        }



        // GET: Product
        public ActionResult Index(string search)
        {
            if (MyProductService.GetAllProduct().Count ==  0){
                return View();
            }


            if (search == null)
                return View(MyProductService.GetAllProduct());
            else
                return View(MyProductService.GetAllProduct().Where(x => x.Name.StartsWith(search) || search == null));
        }

        // GET: MyProducts
        public ActionResult MyProducts()
        {
            var products = MyProductService.GetMyProduct(user.Id);
            // I use this 'if' so that my test '  @if (Model == null) ' in the view works 
            if (products.Count == 0)
            {

                return View();
            }

            return View(products);
        }







        // GET: Product/Create
        public ActionResult Create()
        {
           // ViewBag.CategoryId = new SelectList(db.ProductCategories, "Id", "Name");
            return View();
        }

        // POST: Product/Create
        [HttpPost]
        //public ActionResult Create([Bind(Include = "Name,Price,DateAddition,StockStatus,Description,CategoryId,DirectorId,Quantity,imagePath,imagePath1,imagePath2,imagePath3,imageFile,imageFile1,imageFile2,imageFile3")] Product product,ProductImage model, HttpPostedFileBase imgFromView)
        public ActionResult Create([Bind(Include = "Name,Price,DateAddition,Description,Category,Quantity,Img1,Img2,Img3")] Product product, HttpPostedFileBase imgFromView1, HttpPostedFileBase imgFromView2, HttpPostedFileBase imgFromView3)
        {


            //if (string.IsNullOrWhiteSpace(product.Name))
            //{
            //    ViewBag.ValidationMessageFor = "Le nom du restaurant doit être rempli";
            //    return View(product);
            //}



            //ApplicationUser current_user = UserManager.FindById(System.Web.HttpContext.Current.User.Identity.GetUserId());

            if (!ModelState.IsValid)
            {
                return View(product);
            }

                try
            {
                //product.Name = "haha";
                //product.Price = 55;
                product.DateAddition = DateTime.UtcNow;
                //product.StockStatus = 0;
                product.Description = product.Description;
                //product.CategoryId = 1;
                product.DirectorId = user.Id;
                //product.Quantity = 55;
                product.ProductStatus = true;
                product.Img1 = new byte[imgFromView1.ContentLength];
                imgFromView1.InputStream.Read(product.Img1, 0, imgFromView1.ContentLength);
                product.Img1Base64 = Convert.ToBase64String(product.Img1);


                product.Img2 = new byte[imgFromView2.ContentLength];
                imgFromView2.InputStream.Read(product.Img2, 0, imgFromView2.ContentLength);
                product.Img2Base64 = Convert.ToBase64String(product.Img2);

                product.Img3 = new byte[imgFromView3.ContentLength];
                imgFromView3.InputStream.Read(product.Img3, 0, imgFromView3.ContentLength);
                product.Img3Base64 = Convert.ToBase64String(product.Img3);




                //string filename = Path.GetFileNameWithoutExtension(product.imageFile.FileName);
                //string extension = Path.GetExtension(product.imageFile.FileName);
                //filename = filename + DateTime.Now.ToString("yymmssfff") + extension;
                //string s3filename = filename + DateTime.Now.ToString("yymmssfff") + extension;
                //product.imagePath = "/UploadedProductImages/" + filename;
                //filename = Path.Combine(Server.MapPath("/UploadedProductImages/"), filename);
                //product.imageFile.SaveAs(filename);

                //string filename1 = Path.GetFileNameWithoutExtension(product.imageFile1.FileName);
                //string extension1 = Path.GetExtension(product.imageFile1.FileName);
                //filename1 = filename1 + DateTime.Now.ToString("yymmssfff") + extension1;
                //product.imagePath1 = "/UploadedProductImages/" + filename1;
                //filename1 = Path.Combine(Server.MapPath("/UploadedProductImages/"), filename1);
                //product.imageFile1.SaveAs(filename1);

                //string filename2 = Path.GetFileNameWithoutExtension(product.imageFile2.FileName);
                //string extension2 = Path.GetExtension(product.imageFile2.FileName);
                //filename2 = filename2 + DateTime.Now.ToString("yymmssfff") + extension2;
                //product.imagePath2 = "/UploadedProductImages/" + filename2;
                //filename2 = Path.Combine(Server.MapPath("/UploadedProductImages/"), filename2);
                //product.imageFile2.SaveAs(filename2);

                //string filename3 = Path.GetFileNameWithoutExtension(product.imageFile3.FileName);
                //string extension3 = Path.GetExtension(product.imageFile3.FileName);
                //filename3 = filename3 + DateTime.Now.ToString("yymmssfff") + extension3;
                //product.imagePath = "/UploadedProductImages/" + filename3;
                //filename3 = Path.Combine(Server.MapPath("/UploadedProductImages/"), filename3);
                //product.imageFile3.SaveAs(filename3);



                // ****************************end upload on s3*******************************//

                // for the path and the name of s3 image
                //string filename = Path.GetFileNameWithoutExtension(imgFromView1.FileName);
                //string extension = Path.GetExtension(imgFromView1.FileName);
                //filename = filename + "product added at " + DateTime.Now.ToString("MM dd yyyy HH mm") + "by" + user.UserName + extension;
                //string s3filename = "product-added-by" + user.UserName + "at:" + DateTime.Now.ToString("MM-dd-yyyy-HH-mm") + extension;
                //filename = System.IO.Path.Combine(Server.MapPath("/Content/UploadedProductImages/"), filename);
                //imgFromView1.SaveAs(filename);

                //var s3Client = new AmazonS3Client(accesskey, secretkey, bucketRegion);

                //var fileTransferUtility = new TransferUtility(s3Client);
                //try
                //{

                //    var filePath = filename;
                //    var fileTransferUtilityRequest = new TransferUtilityUploadRequest
                //    {
                //        BucketName = bucketName,
                //        FilePath = filePath,
                //        StorageClass = S3StorageClass.StandardInfrequentAccess,
                //        PartSize = 6291456, // 6 MB.  
                //        Key = s3filename,
                //        CannedACL = S3CannedACL.PublicRead
                //    };
                //    fileTransferUtilityRequest.Metadata.Add("param1", "Value1");
                //    fileTransferUtilityRequest.Metadata.Add("param2", "Value2");
                //    fileTransferUtility.Upload(fileTransferUtilityRequest);
                //    fileTransferUtility.Dispose();

                //    ViewBag.Message = "File Uploaded Successfully!!";
                //}

                //catch (AmazonS3Exception amazonS3Exception)
                //{
                //    if (amazonS3Exception.ErrorCode != null &&
                //        (amazonS3Exception.ErrorCode.Equals("InvalidAccessKeyId")
                //        ||
                //        amazonS3Exception.ErrorCode.Equals("InvalidSecurity")))
                //    {
                //        ViewBag.Message = "Check the provided AWS Credentials.";
                //    }
                //    else
                //    {
                //        ViewBag.Message = "Error occurred: " + amazonS3Exception.Message;
                //    }
                //}

                ////  ****************************end upload on s3 * ******************************//




                if (ModelState.IsValid)
                {
                    MyProductService.Add(product);
                    MyProductService.Commit();

                    //// insertion of image in ProductImage
                    //if (imgFromView != null)
                    //{
                    //    model.Img = new byte[imgFromView.ContentLength];
                    //    imgFromView.InputStream.Read(model.Img, 0, imgFromView.ContentLength);
                    //    model.ProductId = product.Id;
                    //    MyProductImageService.Add(model);
                    //    MyProductImageService.Commit();

                    //}

                }


                // **************************** AWS POLLY SERVICE *******************************//
                //AmazonPollyClient pc = new AmazonPollyClient(accesskey, secretkey, bucketRegion);

                //SynthesizeSpeechRequest sreq = new SynthesizeSpeechRequest();
                //sreq.Text = product.Description;
                //sreq.OutputFormat = OutputFormat.Mp3;
                //sreq.VoiceId = VoiceId.Amy;
                //SynthesizeSpeechResponse sres = pc.SynthesizeSpeech(sreq);
                ////string path = "C:/Users/ahmed/Documents/"+user.UserName+"-"+product.Name+".mp3";
                //string path = Path.Combine(Server.MapPath("/Content/productDescriptionMp3/"), Convert.ToString(product.Id) + ".mp3");

                //using (FileStream fileStream = new FileStream(path, FileMode.Create))
                //{
                //    sres.AudioStream.CopyTo(fileStream);
                //    fileStream.Flush();
                //    fileStream.Close();
                //}
                // **************************** end AWS POLLY SERVICE *******************************//

                return RedirectToAction("Index");
            }
            catch (IOException e)
            {
                throw e;
            }
        }

        // GET: Product/Edit/5
        public ActionResult Edit(int id)
        {
            Product product = MyProductService.GetById(id);
            //ViewBag.CategoryId = new SelectList(db.ProductCategories, "Id", "Name");
            return View(product);
        }

        // POST: Product/Edit/5
        [HttpPost]
        public ActionResult EditCreate([Bind(Include = "Id,Name,Price,DateAddition,ProductStatus,DirectorId,Description,Category,Quantity,Img1,Img2,Img3,Img1Base64,Img2Base64,Img3Base64")] Product product, HttpPostedFileBase imgFromView1, HttpPostedFileBase imgFromView2, HttpPostedFileBase imgFromView3)
        {

            if (imgFromView1 != null)
            {
                product.Img1 = new byte[imgFromView1.ContentLength];
                imgFromView1.InputStream.Read(product.Img1, 0, imgFromView1.ContentLength);
                product.Img1Base64 = Convert.ToBase64String(product.Img1);

            }

            if (imgFromView2 != null)
            {
                product.Img2 = new byte[imgFromView2.ContentLength];
                imgFromView2.InputStream.Read(product.Img2, 0, imgFromView2.ContentLength);
                product.Img2Base64 = Convert.ToBase64String(product.Img2);

            }
            if (imgFromView3 != null)
            {
                product.Img3 = new byte[imgFromView3.ContentLength];
                imgFromView3.InputStream.Read(product.Img3, 0, imgFromView3.ContentLength);
                product.Img3Base64 = Convert.ToBase64String(product.Img3);
            }

            Product newProduct = MyProductService.GetById(product.Id);
            newProduct.Name = product.Name;
            newProduct.Price = product.Price;
            newProduct.Description = product.Description;
            newProduct.Category = product.Category;
            newProduct.Quantity = product.Quantity;
            newProduct.Img1 = product.Img1;
            newProduct.Img2 = product.Img2;
            newProduct.Img3 = product.Img3;
            newProduct.Img1Base64 = product.Img1Base64;
            newProduct.Img2Base64 = product.Img2Base64;
            newProduct.Img3Base64 = product.Img3Base64;
            
            MyProductService.UpdateProduct(newProduct);
            MyProductService.Commit();
            return RedirectToAction("MyProducts");
        }



        //// POST: Product/Delete/5
        //[HttpPost, ActionName("Delete")]
        //public ActionResult Delete(int id)
        //{
        //    Product product = MyProductService.GetById(id);
        //    MyProductService.Delete(product);
        //    MyProductService.Commit();
        //    return RedirectToAction("MyProducts");
        //}



        // GET: Products/Delete/5
        //public ActionResult Delete(int? id)
        //{
        //    if (id == null)
        //    {
        //        return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
        //    }
        //    Product product = db.Products.Find(id);
        //    if (product == null)
        //    {
        //        return HttpNotFound();
        //    }
        //    return View(product);
        //}

        public ActionResult Delete(int id)
        {

            Product product = MyProductService.GetById(id);
            //MyProductService.Delete(product);

            MyProductService.DeleteProduct(id);
            MyProductService.Commit();


            //delete mp3 Description file

            string path = Path.Combine(Server.MapPath("/Content/productDescriptionMp3/"), Convert.ToString(product.Id) + ".mp3");



            if (System.IO.File.Exists(path))
            {
                System.IO.File.Delete(path);
            }



            return RedirectToAction("MyProducts");

        }


        public ActionResult DeleteCartLine(int id)
        {
            CartLine cartLine = MyCartLineService.GetById(id);
            Cart cart = MyCartService.GetById(cartLine.CartId);
            cart.TotalCartPrice -= cartLine.TotalCartLinePrice;
            MyCartService.Update(cart);
            MyCartService.Commit();
            MyCartLineService.DeleteCartLine(id);

            return RedirectToAction("DetailsCart");

        }


        // GET: Products/Details/5
        public ActionResult Details(int id)
        {

            Product product = MyProductService.GetById(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            return View(product);
        }


        //********************************** CART ***********************************************//
        // GET: Cart
        public ActionResult DetailsCart()
        {

            Cart cart = MyCartService.GetCartByParentId(user.Id);
            if (cart != null && cart.TotalCartPrice != 0)
            {
                var cartLines = MyCartLineService.GetCartLinesByCartId(cart.Id);

                var objList = new List<ComplexProductObject>();

                foreach (var cartLine in cartLines)
                {

                    var product = MyProductService.GetById(cartLine.ProductId);
                    ComplexProductObject obj = new ComplexProductObject();
                    obj.ProductName = product.Name;
                    obj.ProductPrice = product.Price;
                    //obj.imagePath = product.imagePath;
                    obj.ChoosedQuantity = cartLine.QuantityChoosed;
                    obj.CartDateAdded = cartLine.DateAddedToCart;
                    obj.TotalCartLinePrice = cartLine.TotalCartLinePrice;
                    obj.availableQuantity = product.Quantity;
                    obj.Img1 = product.Img1;
                    obj.Img2 = product.Img2;
                    obj.Img3 = product.Img3;
                    obj.CartLineId = cartLine.Id;
                    objList.Add(obj);
                }

                return View(objList);
            }

            return View();


        }

        [HttpPost]
        public ActionResult AddToCart()
        {
            int QuantityChoosedFromView = Int16.Parse(Request["quant"].ToString());
            int productId = Int16.Parse(Request["productId"].ToString());
            try
            {

                Cart testCart = MyCartService.GetCartByParentId(user.Id);
                Product product = MyProductService.GetById(productId);
                if (testCart == null)
                {

                    //  why i use db.Carts.Create()  https://stackoverflow.com/questions/6220063/ef-code-first-not-returning-object-as-dynamicproxies-until-after-the-http-reques
                    Cart newCart = db.Carts.Create();
                    newCart.CartStatus = true;
                    newCart.ParentId = user.Id;
                    newCart.PurchaseDate = DateTime.UtcNow;
                    MyCartService.Add(newCart);
                    MyCartService.Commit();


                    CartLine cartLine = db.CartLines.Create();
                    cartLine.DateAddedToCart = DateTime.UtcNow;
                    cartLine.CartId = newCart.Id;
                    cartLine.ProductId = productId;
                    cartLine.QuantityChoosed = QuantityChoosedFromView;
                    cartLine.TotalCartLinePrice = product.Price * QuantityChoosedFromView;
                    cartLine.ProductPrice = product.Price;
                    MyCartLineService.Add(cartLine);
                    MyCartLineService.Commit();


                    newCart.TotalCartPrice += (cartLine.ProductPrice * QuantityChoosedFromView);
                    MyCartService.Commit();


                }
                else
                {

                    CartLine cartLine = db.CartLines.Create();
                    cartLine.DateAddedToCart = DateTime.UtcNow;
                    cartLine.CartId = testCart.Id;
                    cartLine.ProductId = productId;
                    cartLine.QuantityChoosed = QuantityChoosedFromView;
                    cartLine.TotalCartLinePrice = product.Price * QuantityChoosedFromView;
                    cartLine.ProductPrice = product.Price;
                    MyCartLineService.Add(cartLine);
                    MyCartLineService.Commit();



                    testCart.TotalCartPrice += (cartLine.ProductPrice * QuantityChoosedFromView);
                    MyCartService.Commit();



                }
            }

            catch (IOException e)
            {
                throw e;
            }

            return RedirectToAction("Index");



        }


        public void ConfirmPurchase()
        {
            Cart myCart = MyCartService.GetCartByParentId(user.Id);
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
            PointsLoyalty pointsLoyalty = MyPointsLoyaltyService.GetPointsLoyalty(user.Id);
            if (pointsLoyalty == null)
            {

                PointsLoyalty pointsLoyaltyNew = new PointsLoyalty();
                pointsLoyaltyNew.ParentId = user.Id;
                pointsLoyaltyNew.Points = Convert.ToInt32(totalPrice / 3);
                MyPointsLoyaltyService.Add(pointsLoyaltyNew);
                MyPointsLoyaltyService.Commit();

            }
            else
            {
                pointsLoyalty.Points += Convert.ToInt32(totalPrice / 3);
                MyPointsLoyaltyService.Update(pointsLoyalty);
                MyPointsLoyaltyService.Commit();
            }



            //MyProductService.CartConfirmPurchase(user.Id);
            //return RedirectToAction("DetailsCart");
        }


        public ActionResult ConfirmPurchaseView()
        {
            Cart myCart = MyCartService.GetCartByParentId(user.Id);
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
            PointsLoyalty pointsLoyalty = MyPointsLoyaltyService.GetPointsLoyalty(user.Id);
            if (pointsLoyalty == null)
            {

                PointsLoyalty pointsLoyaltyNew = new PointsLoyalty();
                pointsLoyaltyNew.ParentId = user.Id;
                pointsLoyaltyNew.Points = Convert.ToInt32(totalPrice / 3);
                MyPointsLoyaltyService.Add(pointsLoyaltyNew);
                MyPointsLoyaltyService.Commit();

            }
            else
            {
                pointsLoyalty.Points += Convert.ToInt32(totalPrice / 3);
                MyPointsLoyaltyService.Update(pointsLoyalty);
                MyPointsLoyaltyService.Commit();
            }


            return RedirectToAction("Index");

            //MyProductService.CartConfirmPurchase(user.Id);
            //return RedirectToAction("DetailsCart");
        }


        // ************************************************************* Paypal Payment **************************************************************//
        private Payment payment;

        //Create a payment using an APIContext
        private Payment CreatePayment(APIContext apiContext, string redirectUrl)
        {
            Cart myCart = MyCartService.GetCartByParentId(user.Id);
            var cartLines = MyCartLineService.GetCartLinesByCartId(myCart.Id);

            var listItems = new ItemList()
            {
                items = new List<Item>()
            };

            foreach (var cartline in cartLines)
            {
                listItems.items.Add(new Item()
                {
                    name = cartline.MyProduct.Name,
                    currency = "EUR",
                    price = cartline.MyProduct.Price.ToString(),
                    quantity = cartline.QuantityChoosed.ToString(),
                    sku = "sku"
                });

            }
            var payer = new Payer() { payment_method = "paypal" };
            // Do the configuration RedirectURLs here with redirectURLs object
            var redirUrls = new RedirectUrls()
            {
                cancel_url = redirectUrl,
                return_url = redirectUrl
            };
            // Create details object
            var details = new Details()
            {
                tax = "0",
                shipping = "0",
                subtotal = cartLines.Sum(x => x.QuantityChoosed * x.MyProduct.Price).ToString()

            };

            //Create amount object
            var amount = new Amount()
            {
                currency = "EUR",
                //total = (Convert.ToDouble(details.subtotal)).ToString(),
                total = (Convert.ToDouble(details.tax) + Convert.ToDouble(details.shipping) + Convert.ToDouble(details.subtotal)).ToString(),
                details = details

                // tax + shipping + subtotal
            };

            //Create transaction
            var transactionList = new List<Transaction>();
            transactionList.Add(new Transaction()
            {
                description = " Testring transaction description",
                invoice_number = Convert.ToString((new Random()).Next(100000)),
                amount = amount,
                item_list = listItems
            });

            payment = new Payment()
            {
                intent = "sale",
                payer = payer,
                transactions = transactionList,
                redirect_urls = redirUrls
            };
            return payment.Create(apiContext);
        }

        //Create Execute Payment method
        private Payment ExecutePayment(APIContext apiContext, string payerId, string paymentId)
        {
            var paymentExecution = new PaymentExecution()
            {
                payer_id = payerId
            };
            payment = new Payment() { id = paymentId };
            return payment.Execute(apiContext, paymentExecution);
        }

        // Create PaymentWithPaypal method
        public ActionResult PaymentWithPaypal()
        {
            //Gettings context from the paypal bases on clientId and clientSecret for payment
            APIContext apiContext = PaypalConfiguration.GetAPIContext();
            try
            {
                string payerId = Request.Params["PayerID"];
                if (string.IsNullOrEmpty(payerId))
                {
                    // Creating a payment
                    string baseURL = Request.Url.Scheme + "://" + Request.Url.Authority + "/Product/PaymentWithPaypal?";
                    var guid = Convert.ToString((new Random()).Next(100000));
                    var createdPayment = CreatePayment(apiContext, baseURL + "guid=" + guid);
                    // Get links returned from paypal response to create call function
                    var links = createdPayment.links.GetEnumerator();
                    string paypalRedirectUrl = string.Empty;
                    while (links.MoveNext())
                    {
                        Links link = links.Current;
                        if (link.rel.ToLower().Trim().Equals("approval_url"))
                        {
                            paypalRedirectUrl = link.href;
                        }
                    }
                    Session.Add(guid, createdPayment.id);
                    return Redirect(paypalRedirectUrl);

                }
                else
                {
                    // this one will be executed when we have received all the payment params for previous call
                    var guid = Request.Params["guid"];
                    var executePayment = ExecutePayment(apiContext, payerId, Session[guid] as string);
                    if (executePayment.state.ToLower() != "approved")
                    {
                        return View("Failure");
                    }
                }


            }
            catch (Exception ex)
            {
                PaypalLogger.Log("Error: " + ex.Message);
                return View("Failure");
            }
            //MyProductService.CartConfirmPurchase(user.Id);
            ConfirmPurchase();
            return View("Success");
        }
        //****************************************** end paypal *******************************************//

        //aws polly service

        private WaveOutEvent outputDevice;
        private AudioFileReader audioFile;



        public void Polly(int id)
        {
            //AmazonPollyClient pc = new AmazonPollyClient(accesskey, secretkey, bucketRegion);

            //SynthesizeSpeechRequest sreq = new SynthesizeSpeechRequest();
            //sreq.Text = "Your Sample Text Here";
            //sreq.OutputFormat = OutputFormat.Mp3;
            //sreq.VoiceId = VoiceId.Amy;
            //SynthesizeSpeechResponse sres = pc.SynthesizeSpeech(sreq);

            //string path = "C:/Users/ahmed/Documents/fichier.mp3";

            //using (FileStream fileStream = new FileStream(path, FileMode.Create))
            //{
            //    sres.AudioStream.CopyTo(fileStream);
            //    fileStream.Flush();
            //    fileStream.Close();
            //}

            string path = Path.Combine(Server.MapPath("/Content/productDescriptionMp3/"), Convert.ToString(id) + ".mp3");
            outputDevice = new WaveOutEvent();
            audioFile = new AudioFileReader(path);
            outputDevice.Init(audioFile);
            outputDevice.Play();

        }

        //public void NowSayIt(string translatedText, string targetLangagueCode, VoiceId voice)
        //{
        //    using (var client = new AmazonPollyClient(Amazon.RegionEndpoint.USEast1))
        //    {
        //        var request = new Amazon.Polly.Model.SynthesizeSpeechRequest();
        //        request.LanguageCode = targetLangagueCode;
        //        request.Text = translatedText;
        //        request.OutputFormat = OutputFormat.Mp3;
        //        request.VoiceId = voice;
        //        var response = client.SynthesizeSpeechAsync(request).GetAwaiter().GetResult();

        //        string outputFileName = $".\\output-{targetLangagueCode}.mp3";
        //        FileStream output = File.Open(outputFileName, FileMode.Create);
        //        response.AudioStream.CopyTo(output);
        //        output.Close();
        //        PlaySound(outputFileName);
        //    }
        //}

        // WEBSERVICE GET:Product

        public ActionResult GetAllProductWebService()
        {
            var WS_productList = new List<ComplexProductObject>();
            var productList = MyProductService.GetAllProduct();
            foreach (var product in productList)
            {
                ComplexProductObject WS_product = new ComplexProductObject();

                WS_product.ProductName = product.Name;
                WS_product.Img1Base64 = Convert.ToBase64String(product.Img1);

                // WS_product.Img1 = product.Img1;
                WS_productList.Add(WS_product);

            }
            var jsonResult = Json(WS_productList.ToArray(), JsonRequestBehavior.AllowGet);
            jsonResult.MaxJsonLength = int.MaxValue;
            return jsonResult;
        }


        public ActionResult GetCartLinesWebService(int test)
        {
            var WS_productList = new List<ComplexProductObject>();
            var productList = MyProductService.GetAllProduct();
            foreach (var product in productList)
            {
                ComplexProductObject WS_product = new ComplexProductObject();

                WS_product.ProductName = product.Name;
                WS_product.Img1Base64 = Convert.ToBase64String(product.Img1);

                // WS_product.Img1 = product.Img1;
                WS_productList.Add(WS_product);

            }
            var jsonResult = Json(WS_productList.ToArray(), JsonRequestBehavior.AllowGet);
            jsonResult.MaxJsonLength = int.MaxValue;
            return jsonResult;
        }



        public ActionResult GetAllCartByParentIdWebService()
        {
            // her i retrieve all parent cart 
            var cartList = MyCartService.GetAllCartByParentId(1);


            var jsonResult = Json(cartList.ToArray(), JsonRequestBehavior.AllowGet);
            jsonResult.MaxJsonLength = int.MaxValue;
            return jsonResult;


        }


    }
}
