using CloudKids.Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;

namespace CloudKids.Service
{
    public interface IService<T> where T : class
    {
        void Add(T obj);
        void Update(T obj);
        void Delete(T obj);
        void Delete(Expression<Func<T, bool>> Condition);
        void Commit();
        T GetById(int id);
        IEnumerable<T> GetMany(Expression<Func<T, bool>> Condition = null, Expression<Func<T, bool>> orderBy = null);
        IEnumerable<T> GetAll();

        //Cart Service
        T GetCartByParentId(int parentId);


        //CartLine Service
        List<T> GetCartLinesByCartId(int cartId);

        //Product Service (MyProduct)
        List<T> GetMyProduct(int directorId);

        //Product Service deleteProduct
         void DeleteProduct(int productId);

        ////ProductImage Service
        //List<T> GetProductImageByProductId(int productId);

        //Product Service (AllProduct)
        List<T> GetAllProduct();

        //Product Service CartConfirmPurchase (Update CartStatus to false)
        void CartConfirmPurchase(int parentId);

        // Cart Line
        T GetCartLineById(int cartLineId);

        //Product Service deleteCartLine
        void DeleteCartLine(int cartLineId);

        //Cart Service for the web service ( i use carts id in the GetCartLinesByCartIdWebService )
        List<T> GetAllCartByParentId(int parentId);

        //test
        //List<CartLine> GetAllCartLinesByCartId(int cartId);

        T GetNotYetPurchasedCartByParentId(int parentId);

        T GetPointsLoyalty(int parentId);

        void UpdateProduct(Product product);

        T GetLoyaltyPointsByParentId(int parentId);

        void UpdatePointsLoyalties(int points, int parentId);
    }
}