namespace CloudKids.Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Migration18032020 : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Answers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ResponseId = c.Int(nullable: false),
                        QuestionId = c.Int(nullable: false),
                        Value = c.String(),
                        Comment = c.String(),
                        Question_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Questions", t => t.Question_Id)
                .ForeignKey("dbo.Questions", t => t.QuestionId)
                .ForeignKey("dbo.Responses", t => t.ResponseId, cascadeDelete: true)
                .Index(t => t.ResponseId)
                .Index(t => t.QuestionId)
                .Index(t => t.Question_Id);
            
            CreateTable(
                "dbo.Questions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        SurveyId = c.Int(nullable: false),
                        Title = c.String(),
                        Type = c.String(),
                        Body = c.String(),
                        Priority = c.Int(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                        CreatedOn = c.DateTime(),
                        ModifiedOn = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Surveys", t => t.SurveyId, cascadeDelete: true)
                .Index(t => t.SurveyId);
            
            CreateTable(
                "dbo.Responses",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        SurveyId = c.Int(nullable: false),
                        CreatedBy = c.String(),
                        CreatedOn = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Surveys", t => t.SurveyId, cascadeDelete: true)
                .Index(t => t.SurveyId);
            
            CreateTable(
                "dbo.Surveys",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        StartDate = c.DateTime(),
                        EndDate = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Appreciations",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Type = c.Int(nullable: false),
                        PublicationId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Publications", t => t.PublicationId, cascadeDelete: true)
                .Index(t => t.PublicationId);
            
            CreateTable(
                "dbo.Publications",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Contenu = c.String(),
                        DateAdded = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Carte_Paiement",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Type = c.Int(nullable: false),
                        Numero = c.String(nullable: false, maxLength: 50),
                        DateExpiration = c.DateTime(nullable: false),
                        Pays = c.String(),
                        Adresse = c.String(),
                        Ville = c.String(),
                        Region = c.String(),
                        CodePostal = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.CartLines",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        DateAdded = c.DateTime(nullable: false),
                        Quantity = c.Int(nullable: false),
                        Price = c.Double(nullable: false),
                        ProductId = c.Int(nullable: false),
                        CartId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Carts", t => t.CartId, cascadeDelete: true)
                .ForeignKey("dbo.Products", t => t.ProductId, cascadeDelete: true)
                .Index(t => t.ProductId)
                .Index(t => t.CartId);
            
            CreateTable(
                "dbo.Carts",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Quantity = c.Int(nullable: false),
                        Price = c.Double(nullable: false),
                        ParentId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.CommandHistories",
                c => new
                    {
                        Id = c.Int(nullable: false),
                        DateAdded = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Carts", t => t.Id)
                .Index(t => t.Id);
            
            CreateTable(
                "dbo.Products",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Price = c.Double(nullable: false),
                        Quantity = c.Int(nullable: false),
                        DateAddition = c.DateTime(nullable: false),
                        imagePath = c.String(),
                        imagePath1 = c.String(),
                        imagePath2 = c.String(),
                        imagePath3 = c.String(),
                        StockStatus = c.Int(nullable: false),
                        Description = c.String(),
                        CategoryId = c.Int(),
                        DirectorId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ProductCategories", t => t.CategoryId)
                .ForeignKey("dbo.Profils", t => t.DirectorId)
                .Index(t => t.CategoryId)
                .Index(t => t.DirectorId);
            
            CreateTable(
                "dbo.ProductCategories",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Profils",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Statut = c.Int(nullable: false),
                        Calendar = c.DateTime(nullable: false),
                        Email = c.String(),
                        Username = c.String(),
                        Password = c.String(),
                        Name = c.String(),
                        Gender = c.Int(),
                        Phone = c.String(),
                        Image = c.String(),
                        Adresse_Street = c.String(),
                        Adresse_CodePostal = c.Int(nullable: false),
                        Adresse_Ville = c.String(),
                        Age = c.Int(),
                        DirecteurId = c.Int(),
                        Desciption = c.String(),
                        DateCreation = c.DateTime(),
                        NbreEmployees = c.Int(),
                        Localisation = c.String(),
                        Prix = c.Double(),
                        Discriminator = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.DirecteurId)
                .Index(t => t.DirecteurId);
            
            CreateTable(
                "dbo.Commentaires",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Contenu = c.String(),
                        DateAdded = c.DateTime(nullable: false),
                        PublicationId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Publications", t => t.PublicationId, cascadeDelete: true)
                .Index(t => t.PublicationId);
            
            CreateTable(
                "dbo.Enfants",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        BirthDate = c.DateTime(nullable: false),
                        Gender = c.Int(nullable: false),
                        ParentId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.ParentId, cascadeDelete: true)
                .Index(t => t.ParentId);
            
            CreateTable(
                "dbo.Evenements",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Host = c.String(nullable: false, maxLength: 25),
                        Description = c.String(nullable: false, maxLength: 250),
                        Subject = c.String(),
                        Start = c.DateTime(nullable: false),
                        End = c.DateTime(nullable: false),
                        ThemeColor = c.String(),
                        IsFullDay = c.Boolean(nullable: false),
                        Lieu = c.String(nullable: false, maxLength: 125),
                        Tel = c.String(nullable: false),
                        EmailContact = c.String(nullable: false),
                        image = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.ProductComments",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        DateAdded = c.DateTime(nullable: false),
                        Content = c.String(),
                        ParentId = c.Int(nullable: false),
                        ProductId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.ParentId, cascadeDelete: true)
                .ForeignKey("dbo.Products", t => t.ProductId, cascadeDelete: true)
                .Index(t => t.ParentId)
                .Index(t => t.ProductId);
            
            CreateTable(
                "dbo.RDVs",
                c => new
                    {
                        ParentFk = c.Int(nullable: false),
                        DirecteurFk = c.Int(nullable: false),
                        Id = c.Int(nullable: false),
                        DateRdv = c.DateTime(nullable: false),
                        LieuRdv = c.String(),
                        MotifRdv = c.String(),
                    })
                .PrimaryKey(t => new { t.ParentFk, t.DirecteurFk })
                .ForeignKey("dbo.Profils", t => t.DirecteurFk, cascadeDelete: true)
                .ForeignKey("dbo.Profils", t => t.ParentFk)
                .Index(t => t.ParentFk)
                .Index(t => t.DirecteurFk);
            
            CreateTable(
                "dbo.Reclamations",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Motif = c.String(nullable: false),
                        ProfilJardinId = c.Int(nullable: false),
                        ProfilParentId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.ProfilJardinId, cascadeDelete: true)
                .ForeignKey("dbo.Profils", t => t.ProfilParentId)
                .Index(t => t.ProfilJardinId)
                .Index(t => t.ProfilParentId);
            
            CreateTable(
                "dbo.Reputations",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false, maxLength: 50),
                        Type = c.Int(nullable: false),
                        Prix = c.Double(nullable: false),
                        ProfilJardinId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.ProfilJardinId, cascadeDelete: true)
                .Index(t => t.ProfilJardinId);
            
            CreateTable(
                "dbo.Reunions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        DateReunion = c.DateTime(nullable: false),
                        LieuReunion = c.String(),
                        MotifReunion = c.String(),
                        ProfilDirecteurId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Profils", t => t.ProfilDirecteurId, cascadeDelete: true)
                .Index(t => t.ProfilDirecteurId);
            
            CreateTable(
                "dbo.Streamings",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Visite_Virtuelle",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Reunions", "ProfilDirecteurId", "dbo.Profils");
            DropForeignKey("dbo.Reputations", "ProfilJardinId", "dbo.Profils");
            DropForeignKey("dbo.Reclamations", "ProfilParentId", "dbo.Profils");
            DropForeignKey("dbo.Reclamations", "ProfilJardinId", "dbo.Profils");
            DropForeignKey("dbo.RDVs", "ParentFk", "dbo.Profils");
            DropForeignKey("dbo.RDVs", "DirecteurFk", "dbo.Profils");
            DropForeignKey("dbo.Profils", "DirecteurId", "dbo.Profils");
            DropForeignKey("dbo.ProductComments", "ProductId", "dbo.Products");
            DropForeignKey("dbo.ProductComments", "ParentId", "dbo.Profils");
            DropForeignKey("dbo.Enfants", "ParentId", "dbo.Profils");
            DropForeignKey("dbo.Commentaires", "PublicationId", "dbo.Publications");
            DropForeignKey("dbo.CartLines", "ProductId", "dbo.Products");
            DropForeignKey("dbo.Products", "DirectorId", "dbo.Profils");
            DropForeignKey("dbo.Products", "CategoryId", "dbo.ProductCategories");
            DropForeignKey("dbo.CartLines", "CartId", "dbo.Carts");
            DropForeignKey("dbo.CommandHistories", "Id", "dbo.Carts");
            DropForeignKey("dbo.Appreciations", "PublicationId", "dbo.Publications");
            DropForeignKey("dbo.Responses", "SurveyId", "dbo.Surveys");
            DropForeignKey("dbo.Questions", "SurveyId", "dbo.Surveys");
            DropForeignKey("dbo.Answers", "ResponseId", "dbo.Responses");
            DropForeignKey("dbo.Answers", "QuestionId", "dbo.Questions");
            DropForeignKey("dbo.Answers", "Question_Id", "dbo.Questions");
            DropIndex("dbo.Reunions", new[] { "ProfilDirecteurId" });
            DropIndex("dbo.Reputations", new[] { "ProfilJardinId" });
            DropIndex("dbo.Reclamations", new[] { "ProfilParentId" });
            DropIndex("dbo.Reclamations", new[] { "ProfilJardinId" });
            DropIndex("dbo.RDVs", new[] { "DirecteurFk" });
            DropIndex("dbo.RDVs", new[] { "ParentFk" });
            DropIndex("dbo.ProductComments", new[] { "ProductId" });
            DropIndex("dbo.ProductComments", new[] { "ParentId" });
            DropIndex("dbo.Enfants", new[] { "ParentId" });
            DropIndex("dbo.Commentaires", new[] { "PublicationId" });
            DropIndex("dbo.Profils", new[] { "DirecteurId" });
            DropIndex("dbo.Products", new[] { "DirectorId" });
            DropIndex("dbo.Products", new[] { "CategoryId" });
            DropIndex("dbo.CommandHistories", new[] { "Id" });
            DropIndex("dbo.CartLines", new[] { "CartId" });
            DropIndex("dbo.CartLines", new[] { "ProductId" });
            DropIndex("dbo.Appreciations", new[] { "PublicationId" });
            DropIndex("dbo.Responses", new[] { "SurveyId" });
            DropIndex("dbo.Questions", new[] { "SurveyId" });
            DropIndex("dbo.Answers", new[] { "Question_Id" });
            DropIndex("dbo.Answers", new[] { "QuestionId" });
            DropIndex("dbo.Answers", new[] { "ResponseId" });
            DropTable("dbo.Visite_Virtuelle");
            DropTable("dbo.Streamings");
            DropTable("dbo.Reunions");
            DropTable("dbo.Reputations");
            DropTable("dbo.Reclamations");
            DropTable("dbo.RDVs");
            DropTable("dbo.ProductComments");
            DropTable("dbo.Evenements");
            DropTable("dbo.Enfants");
            DropTable("dbo.Commentaires");
            DropTable("dbo.Profils");
            DropTable("dbo.ProductCategories");
            DropTable("dbo.Products");
            DropTable("dbo.CommandHistories");
            DropTable("dbo.Carts");
            DropTable("dbo.CartLines");
            DropTable("dbo.Carte_Paiement");
            DropTable("dbo.Publications");
            DropTable("dbo.Appreciations");
            DropTable("dbo.Surveys");
            DropTable("dbo.Responses");
            DropTable("dbo.Questions");
            DropTable("dbo.Answers");
        }
    }
}
