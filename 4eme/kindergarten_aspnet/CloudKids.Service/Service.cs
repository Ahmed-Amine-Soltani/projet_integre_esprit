using CloudKids.Data.Infrastructure;
using CloudKids.Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Service
{
    public class Service<T> : IService<T> where T : class
    {
        IRepository<T> repo;
        IUnitOfWork uow;
        public Service(IUnitOfWork _uow)
        {
            uow = _uow;
            repo = uow.GetRepository<T>();
        }
        public void Add(T obj)
        {
            repo.Add(obj);
        }
        public void Update(T obj)
        {
            repo.Update(obj);
        }
        public void Delete(T obj)
        {
            repo.Delete(obj);
        }
        public void Delete(Expression<Func<T, bool>> condition)
        {
            repo.Delete(condition);
        }
        public void Commit()
        {
            repo.Commit();
        }

        public T GetById(int id)
        {
            return repo.GetById(id);
        }
        public IEnumerable<T> GetMany(Expression<Func<T, bool>> condition = null, Expression<Func<T, bool>> orderBy = null)
        {
            return repo.GetMany(condition, orderBy);
        }
        public IEnumerable<T> GetAll()
        {
            return repo.GetAll();
        }

        //Cart Service
        public T GetCartByParentId(int parentId)
        {
            return repo.GetCartByParentId(parentId);

        }


        //CartLine Service
        public List<T> GetCartLinesByCartId(int cartId)
        {
            return repo.GetCartLinesByCartId(cartId);

        }

        //Product Service (MyProduct)
        public List<T> GetMyProduct(int directorId)
        {
            return repo.GetMyProduct(directorId);

        }

        // Cart Line
       public T GetCartLineById(int cartLineId)
        {
            return repo.GetCartLineById(cartLineId);
        }


        public void DeleteProduct(int productId)
        {
            repo.DeleteProduct(productId);
        }


        //Product Service (AllProduct)
        public List<T> GetAllProduct() {
           return repo.GetAllProduct();
        }


        //Product Service CartConfirmPurchase (Update CartStatus to false)
        public void CartConfirmPurchase(int parentId)
        {
            repo.CartConfirmPurchase(parentId);
        }

        //Product Service deleteCartLine
        public void DeleteCartLine(int cartLineId)
        {
            repo.DeleteCartLine(cartLineId);
        }

        //ProductImage Service
        //public List<T> GetProductImageByProductId(int productId)
        //{
        //    return repo.GetProductImageByProductId(productId);
        //}

        //Cart Service for the web service ( i use carts id in the GetCartLinesByCartIdWebService )
        public List<T> GetAllCartByParentId(int parentId)
        {
            return repo.GetAllCartByParentId(parentId);
        }


        //test
        //public List<CartLine> GetAllCartLinesByCartId(int cartId)
        //{
        //    return repo.GetAllCartLinesByCartId(cartId);
        //}

        public T GetNotYetPurchasedCartByParentId(int parentId)
        {
            return repo.GetNotYetPurchasedCartByParentId(parentId);
        }

        public T GetPointsLoyalty(int parentId)
        {
            return repo.GetPointsLoyalty(parentId);
        }

        public void UpdateProduct(Product product)
        {
              repo.UpdateProduct(product);
        }

        public T GetLoyaltyPointsByParentId(int parentId)
        {
            return repo.GetLoyaltyPointsByParentId(parentId);
        }

        public void UpdatePointsLoyalties(int points, int parentId)
        {
            repo.UpdatePointsLoyalties(points, parentId);
        }
    }
}
