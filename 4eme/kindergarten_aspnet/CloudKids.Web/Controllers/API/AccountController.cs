using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using CloudKids.Web.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;

namespace CloudKids.Web.Controllers.API
{
    public class AccountController : BaseApiController
    {
        private UserManager<IdentityUser> _userManager;

        [Route("api/users")]
        public IHttpActionResult GetUsers()
        {
            IList<dynamic> list = new List<dynamic>();

            foreach (var user in this.AppUserManager.Users)
            {
                list.Add(this.TheModelFactory.Create(user));
            }

            return Ok(list);
        }

        //[Route("api/user/{id:guid}", Name = "GetUserById")]
        //public async Task<IHttpActionResult> GetUser(int id)
        //{
        //    var user = await this.AppUserManager.FindByIdAsync(id);

        //    if (user != null)
        //    {
        //        return Ok(this.TheModelFactory.Create(user));
        //    }

        //    return NotFound();

        //}

        //[Route("api/user/{username}")]
        //public async Task<IHttpActionResult> GetUserByName(string username)
        //{
        //    var user = await this.AppUserManager.FindByNameAsync(username);

        //    if (user != null)
        //    {
        //        return Ok(this.TheModelFactory.Create(user));
        //    }

        //    return NotFound();

        //}

        //[Route("api/user/{email}")]
        //public async Task<IHttpActionResult> GetUserByEmail(string email)
        //{
        //    var user = await this.AppUserManager.FindByEmailAsync(email);

        //    if (user != null)
        //    {
        //        return Ok(this.TheModelFactory.Create(user));
        //    }

        //    return NotFound();

        //}

        //[Route("api/user/{email}/{password}")]
        //public async Task<IHttpActionResult> FindUser(string email, string password)
        //{
        //    var user = await this.AppUserManager.FindAsync(email, password);

        //    if (user != null)
        //    {
        //        return Ok(this.TheModelFactory.Create(user));
        //    }

        //    return null;

        //}

        [System.Web.Http.Route("api/user/{email}/{password}")]
        [System.Web.Http.HttpGet]
        public IHttpActionResult FindUser(string email, string password)
        {
            var user = this.AppUserManager.FindAsync(email, password);

            if (user != null)
            {
                return Json(user);
            }

            return null;

        }


        //[Route("api/create")]
        //public async Task<IHttpActionResult> CreateUser(CreateUserBindingModel createUserModel)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    var user = new ApplicationUser()
        //    {
        //        UserName = createUserModel.Username,
        //        Email = createUserModel.Email
        //    };

        //    IdentityResult addUserResult = await this.AppUserManager.CreateAsync(user, createUserModel.Password);

        //    if (!addUserResult.Succeeded)
        //    {
        //        return GetErrorResult(addUserResult);
        //    }

        //    Uri locationHeader = new Uri(Url.Link("GetUserById", new { id = user.Id }));

        //    return Created(locationHeader, TheModelFactory.Create(user));
        //}
    }
}
