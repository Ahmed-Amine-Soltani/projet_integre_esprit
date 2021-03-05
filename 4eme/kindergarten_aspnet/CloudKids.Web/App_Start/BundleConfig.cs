using System.Web;
using System.Web.Optimization;

namespace CloudKids.Web
{
    public class BundleConfig
    {
        // For more information on bundling, visit http://go.microsoft.com/fwlink/?LinkId=301862
        public static void RegisterBundles(BundleCollection bundles)
        {
            bundles.Add(new ScriptBundle("~/bundles/jquery").Include(
                        "~/Scripts/jquery-{version}.js"));

            bundles.Add(new ScriptBundle("~/bundles/jqueryval").Include(
                        "~/Scripts/jquery.validate*"));

            // Use the development version of Modernizr to develop with and learn from. Then, when you're
            // ready for production, use the build tool at http://modernizr.com to pick only the tests you need.
            bundles.Add(new ScriptBundle("~/bundles/modernizr").Include(
                        "~/Scripts/modernizr-*"));

            bundles.Add(new ScriptBundle("~/bundles/js").Include(
                      "~/Scripts/js/jquery.min.js",
                      "~/Scripts/js/popper.js",
                      "~/Scripts/js/bootstrap.min.js",
                      "~/Scripts/lib/slick/slick.min.js",
                      "~/Scripts/js/html5lightbox.js",
                      "~/Scripts/revolution/js/jquery.themepunch.tools.min.js",
                      "~/Scripts/revolution/js/jquery.themepunch.revolution.min.js",
                      "~/Scripts/revolution/js/revolution.extension.actions.min.js",
                      "~/Scripts/revolution/js/revolution.extension.carousel.min.js",
                      "~/Scripts/revolution/js/revolution.extension.kenburn.min.js",
                      "~/Scripts/revolution/js/revolution.extension.layeranimation.min.js",
                      "~/Scripts/revolution/js/revolution.extension.migration.min.js",
                      "~/Scripts/revolution/js/revolution.extension.navigation.min.js",
                      "~/Scripts/revolution/js/revolution.extension.parallax.min.js",
                      "~/Scripts/revolution/js/revolution.extension.slideanims.min.js",
                      "~/Scripts/revolution/js/revolution.extension.video.min.js",
                      "~/Scripts/revolution/js/revolution.initialize.js",
                      "~/Scripts/js/script.js"


                      ));

            bundles.Add(new ScriptBundle("~/bundles/bootstrap").Include(
                "~/Scripts/bootstrap.js",
                "~/Scripts/respond.js"));

            bundles.Add(new StyleBundle("~/Content/css").Include(
                      "~/Content/bootstrap.css",
                      "~/Content/site.css"
                     
            ));

            bundles.Add(new StyleBundle("~/Content/css/css").Include(
                        "~/Content/css/animate.css",
                        "~/Content/css/bootstrap.min.css",
                        "~/Content/css/jquery.range.css",
                        "~/Content/css/font-awesome.min.css",
                        "~/Content/css/select2.css",
                        "~/Content/css/style.css",
                        "~/Content/css/select2.css",
                        "~/Content/revolution/css/settings.css",
                        "~/Content/revolution/css/navigation.css",
                        "~/Content/lib/slick/slick.css",
                        "~/Content/lib/slick/slick-theme.css"
                        ));

            bundles.Add(new StyleBundle("~/Content/font/css").Include(
                "~/Content/font/all.css"
            ));

            bundles.Add(new ScriptBundle("~/bundles/font/js").Include(
                "~/Scripts/font/all.js"
            ));




            

            //
            bundles.Add(new StyleBundle("~/Content/bootstrap-survey")
                            .Include("~/Content/bootstrap-survey.css")
                            .Include("~/Content/bootstrap-datepicker.css"));

            bundles.Add(new StyleBundle("~/Content/cleditor")
                            .Include("~/Content/jquery.cleditor.css"));

            bundles.Add(new StyleBundle("~/Content/toastr")
                            .Include("~/Content/toastr.css"));

            bundles.Add(new StyleBundle("~/Content/site")
                            .Include("~/Content/site.css"));

            // ====================================================================================
            // SCRIPTS
            // ====================================================================================

            bundles.Add(new ScriptBundle("~/bundles/bootstrap")
                            .Include("~/Scripts/bootstrap.js")
                            .Include("~/Scripts/bootstrap-datepicker.js"));

            bundles.Add(new ScriptBundle("~/bundles/jquery")
                            .Include("~/Scripts/jquery-{version}.js")
                            .Include("~/Scripts/jquery-migrate-1.1.1.js"));

            bundles.Add(new ScriptBundle("~/bundles/jqueryval")
                            .Include("~/Scripts/jquery.unobtrusive*")
                            .Include("~/Scripts/jquery.validate*"));

            bundles.Add(new ScriptBundle("~/bundles/cleditor")
                            .Include("~/Scripts/jquery.cleditor.js")
                            .Include("~/Scripts/knockout.cleditor.js"));

            bundles.Add(new ScriptBundle("~/bundles/knockout")
                            .Include("~/Scripts/knockout-{version}.js")
                            .Include("~/Scripts/knockout.mapping-latest.js")
                            .Include("~/Scripts/knockout.enter.js")
                            .Include("~/Scripts/knockout.validation.js"));

            bundles.Add(new ScriptBundle("~/bundles/toastr")
                            .Include("~/Scripts/toastr.js"));

            bundles.Add(new ScriptBundle("~/bundles/models")
                            .Include("~/Scripts/vm.department.js")
                            .Include("~/Scripts/vm.question.js")
                            .Include("~/Scripts/vm.responselist.js")
                            .Include("~/Scripts/vm.survey.js")
                            .Include("~/Scripts/vm.surveylist.js"));

            bundles.Add(new ScriptBundle("~/bundles/modernizr")
                            .Include("~/Scripts/modernizr-*"));


        }
    }
}
