using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace CloudKids.Domain.Entities
{
    public class Product
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "Product Name can not be null")]
        [StringLength(20, ErrorMessage = "Product Name cannot be longer than 20 characters.")]
        public string Name { get; set; }
        [Required(ErrorMessage = "Product Price can not be null")]
        [Range(1, int.MaxValue, ErrorMessage = "Only positive number allowed")]
        public Double Price { get; set; }
        [Required(ErrorMessage = "Product Quantity can not be null")]
        [Range(1, int.MaxValue, ErrorMessage = "Only positive number allowed")]
        public int Quantity { get; set; }

        public DateTime DateAddition { get; set; }

        [JsonIgnore]
        public byte[] Img1 { get; set; }

        public string Img1Base64 { get; set; }

        public string Img2Base64 { get; set; }

        public string Img3Base64 { get; set; }

        [JsonIgnore]
        public byte[] Img2 { get; set; }
        [JsonIgnore]
        public byte[] Img3 { get; set; }




        //public String imagePath { get; set; }

        //public String imagePath1 { get; set; }

        //public String imagePath2 { get; set; }

        //public String imagePath3 { get; set; }

        //[NotMapped]
        //public HttpPostedFileBase imageFile { get; set; }

        //[NotMapped]
        //public HttpPostedFileBase imageFile1 { get; set; }
        //[NotMapped]
        //public HttpPostedFileBase imageFile2 { get; set; }
        //[NotMapped]
        //public HttpPostedFileBase imageFile3 { get; set; }

        public bool ProductStatus { get; set; }

        [DataType(DataType.MultilineText)]
        [Required(ErrorMessage = "Product Description can not be null")]
        [StringLength(50, ErrorMessage = "Product Name cannot be longer than 50 characters.")]
        public string Description { get; set; }

        //[JsonIgnore]
        //public ProductCategory Category { get; set; }

        //[ForeignKey("Category")]

        [Required(ErrorMessage = "The Product Category can not be null")]
        public String Category { get; set; }

        //public virtual IEnumerable<ProductComment> ProductComments { get; set; }
        //public Profil_Directeur ProfilDirecteur { get; set; }

        //[ForeignKey("ProfilDirecteur")]
        public int DirectorId { get; set; }

        //public virtual IEnumerable<CartLine> CartLines { get; set; }


    }

   
}
