using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Migrations;
using System.Linq;
using System.Linq.Expressions;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Data;
using CloudKids.Domain;
using CloudKids.Domain.Entities;
using System.Data.SqlClient;
using System.Configuration;

namespace CloudKids.Data.Infrastructure
{
    public class Repository<T> : IRepository<T> where T : class
    {
        private CloudKidsContext context;
        private DbSet<T> dbSet;


        public Repository(IDBFactory dbFactory)
        {
            this.context = dbFactory.Context;
            this.dbSet = context.Set<T>();
        }

        public void Add(T obj)
        {
            dbSet.Add(obj);
        }

        public void Update(T obj)
        {
            dbSet.Attach(obj);
            context.Entry(obj).State = EntityState.Modified;

        }

        public void Delete(T obj)
        {
            dbSet.Remove(obj);
        }

        public void Delete(Expression<Func<T, bool>> condition) // lambda expression
        {
            var result = dbSet.Where(condition);

            if (result != null)
                foreach (T obj in result)
                    dbSet.Remove(obj);
        }

        public T GetById(int id)
        {
            return dbSet.Find(id);
        }

        public IEnumerable<T> GetAll()
        {
            return dbSet.AsEnumerable();
        }

        public IEnumerable<T> GetMany(Expression<Func<T, bool>> condition = null, Expression<Func<T, bool>> orderBy = null)
        {
            return dbSet.Where(condition).OrderBy(orderBy);
        }


        public void Commit()
        {
            context.SaveChanges();
        }

        //Cart Service for asp project , i test the CartStatus
        public T GetCartByParentId(int parentId)
        {
            
            return dbSet.SqlQuery("Select * from Carts where ParentId=@parentId and CartStatus = 'true'", new SqlParameter("@parentId", parentId)).FirstOrDefault();
        }

        //Cart Service for the web service ( i use carts id in the GetCartLinesByCartIdWebService )
        public List<T> GetAllCartByParentId(int parentId)
        {
    //        var cartList = context.Carts
    //.Where(o => o.ParentId == parentId) // your fk1
    //.Select(o => o) // your foreignKey2 ???
    //.ToList();


            //return context.Carts.SqlQuery("Select * from Carts where ParentId= 1 and CartStatus = 'false'").ToList();

            return dbSet.SqlQuery("Select * from Carts where ParentId=@parentId and CartStatus = 'false'", new SqlParameter("@parentId", parentId)).ToList<T>();
        }


        public T GetNotYetPurchasedCartByParentId(int parentId)
        {

                    var cartList = context.Carts
            .Where(o => o.ParentId == parentId).Where(o => o.CartStatus == true) // your fk1
            .Select(o => o) // your foreignKey2 ???
            .ToList();

            return dbSet.SqlQuery("Select * from Carts where ParentId=@parentId  and CartStatus = 'true'", new SqlParameter("@parentId", parentId)).FirstOrDefault();
        }

        public T GetCartLineById(int cartLineId)
        {
            return dbSet.SqlQuery("Select * from CartLines where Id=@cartLineId", new SqlParameter("@cartLineId", cartLineId)).FirstOrDefault();
        }

        //CartLine Service
        public List<T> GetCartLinesByCartId(int cartId)
        {
            return dbSet.SqlQuery("Select * from CartLines where CartId=@cartId", new SqlParameter("@cartId", cartId)).ToList<T>();
        }

        //Product Service (MyProduct)
        public List<T> GetMyProduct(int directorId)
        {
            return dbSet.SqlQuery("Select * from Products where DirectorId=@directorId and ProductStatus = 'true'", new SqlParameter("@directorId", directorId)).ToList<T>();
        }

        //Product Service (AllProduct)
        public List<T> GetAllProduct()
        {
            return dbSet.SqlQuery("Select * from Products where ProductStatus = 'true'").ToList<T>();
        }

        //Product Service deleteProduct ( update ProductStatus to false )
        public void DeleteProduct(int productId)
        {
            context.Database.ExecuteSqlCommand("UPDATE Products SET ProductStatus = 'false' WHERE Id = @productId", new SqlParameter("@productId", productId));


        }

        //Product Service CartConfirmPurchase (Update CartStatus to false)
        public void CartConfirmPurchase(int parentId)
        {
            context.Database.ExecuteSqlCommand("UPDATE Carts SET CartStatus = 'false' WHERE ParentId = @parentId", new SqlParameter("@parentId", parentId));
            

        }

        //Product Service deleteCartLine
        public void DeleteCartLine(int cartLineId)
        {
            context.Database.ExecuteSqlCommand("DELETE FROM CartLines WHERE Id = @cartLineId", new SqlParameter("@cartLineId", cartLineId));


        }

        //Product Service deleteCartLine
        public T GetPointsLoyalty(int parentId)
        {
            return dbSet.SqlQuery("select * from PointsLoyalties where ParentId = @parentId", new SqlParameter("@parentId", parentId)).FirstOrDefault();
        }

        //    public List<CartLine> GetAllCartLinesByCartId(int cartId)
        //    {
        //        var cartLinesList = context.Carts
        //.Where(o => o.Id == cartId) // your fk1
        //.Select(o => o.CartLines) // your foreignKey2 ???
        //.ToList();



        //        List<CartLine> newCartLinesList = new List<CartLine>();
        //        foreach (var cartLine in cartLinesList)
        //        {


        //        }

        //        return newCartLinesList;
        //    }

        ////ProductImage Service
        //public List<T> GetProductImageByProductId(int productId)
        //{
        //    return dbSet.SqlQuery("Select * from ProductImages where ProductId=@productId", new SqlParameter("@productId", productId)).ToList<T>();
        //}

        public void UpdateProduct(Product product)
        {
            int Id = product.Id;
            string Name = product.Name;
            Double Price = product.Price;
            int Quantity = product.Quantity;
            DateTime DateAddition = product.DateAddition;
            byte[] Img1 = product.Img1;
            string Img1Base64 = product.Img1Base64;
            string Img2Base64 = product.Img2Base64;
            string Img3Base64 = product.Img3Base64;
            byte[] Img2 = product.Img2;
            byte[] Img3 = product.Img3;
            bool ProductStatus = product.ProductStatus;
            string Description = product.Description;
            string Category = product.Category;
            int DirectorId = product.DirectorId;
        //public virtual IEnumerable<CartLine> CartLines  =  product.
        context.Database.ExecuteSqlCommand("UPDATE Products SET Name = @Name , Price = @Price , Quantity = @Quantity , Img1 = @Img1 , Img2 = @Img2 , Img3=@Img3,Img1Base64=@Img1Base64 , Img2Base64=@Img2Base64 , Img3Base64=@Img3Base64 ,Description=@Description , Category = @Category  WHERE Id = @Id", 
            new SqlParameter("@Id", Id),
            new SqlParameter("@Name", Name),
            new SqlParameter("@Price", Price),
            new SqlParameter("@Quantity", Quantity),
            new SqlParameter("@Img1", Img1),
            new SqlParameter("@Img2", Img2),
            new SqlParameter("@Img3", Img3),
            new SqlParameter("@Img1Base64", Img1Base64),
            new SqlParameter("@Img2Base64", Img2Base64),
            new SqlParameter("@Img3Base64", Img3Base64),
            new SqlParameter("@Description", Description),
            new SqlParameter("@Category", Category)
            );


        }



        public T GetLoyaltyPointsByParentId(int parentId)
        {

            return dbSet.SqlQuery("Select * from PointsLoyalties where ParentId=@parentId ", new SqlParameter("@parentId", parentId)).FirstOrDefault();
        }


        public void UpdatePointsLoyalties(int points, int parentId)
        {
            context.Database.ExecuteSqlCommand("UPDATE PointsLoyalties SET Points = @points  WHERE ParentId = @parentId", new SqlParameter("@parentId", parentId), new SqlParameter("@points", points));


        }


    }



}
