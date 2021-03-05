﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace CloudKids.Web
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");



            routes.MapRoute(
                name: "ProfilParent",
                url: "Profil_Parent/Index",
                defaults: new { controller = "Profil_Parent", action = "Index" });


            routes.MapRoute(
                name: "Root",
                url: "DashboardParentSurvey",
                defaults: new { controller = "DashboardParentSurvey", action = "Index" });

            routes.MapRoute(
                name: "Responses",
                url: "Surveys/{surveyId}/Responses/{action}/{id}",
                defaults: new { controller = "Responses", action = "Index", id = UrlParameter.Optional });


            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
