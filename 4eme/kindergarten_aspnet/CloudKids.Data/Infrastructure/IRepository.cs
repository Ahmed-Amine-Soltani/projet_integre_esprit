using CloudKids.Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Data.Infrastructure
{
    public interface IRepository<T> where T : class 
    {
        void Add(T obj);

        void Update(T obj);

        void Delete(T obj);

        void Delete(Expression<Func<T, bool>> condition);

        T GetById(int id);

        IEnumerable<T> GetAll();

        IEnumerable<T> GetMany(Expression<Func<T, bool>> condition = null, Expression<Func<T, bool>> orderBy = null);

        void Commit();

        //Cart Service
        T GetCartByParentId(int parentId);

        //CartLine Service
        List<T> GetCartLinesByCartId(int cartId);

        //Product Service (MyProduct)
        List<T> GetMyProduct(int directoId);

        ////ProductImage Service
        //List<T> GetProductImageByProductId(int productId);

        //Product Service deleteProduct
        void DeleteProduct(int productId);

        //Product Service (AllProduct)
        List<T> GetAllProduct();

     
        T GetCartLineById(int cartLineId);

            //Product Service CartConfirmPurchase (Update CartStatus to false)
            void CartConfirmPurchase(int parentId);

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
