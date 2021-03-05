<?php
/**
 * Created by PhpStorm.
 * User: Samplon
 * Date: 16/02/2019
 * Time: 13:19
 */

namespace PromoBundle\Controller;


use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use ProduitBundle\Entity\Produit;
use PromoBundle\Form\PromotionType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use PromoBundle\Entity\Promotion;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Validator\Constraints\DateTime;

class PromoController extends Controller
{
    public function redirectionAction()
    {
        return $this->render('@Promo/Default/index.html.twig');
    }
    public function produitsoldeAction()
    {
        return $this->render('@Promo/Default/affiche.html.twig');
    }

    public function AfficherAction(Request $request)
    {
        $d=$this->getDoctrine()->getRepository(Promotion::class)->findAll();
        /**
         * @var $paginator \Knp\Component\Pager\Paginator
         */
        $paginator= $this->get('knp_paginator');
        $rs=$paginator->paginate(
            $d,
            $request->query->getInt('page',1),
            $request->query->getInt('limit',3)



        );
        return $this->render('@Promo/Default/index.html.twig', array("d"=>$rs
        ));
    }


    public function modifierAction($id,Request $request)
    {
        $etudiant=new Promotion();

        //0-preparation entity manager
        $em=$this->getDoctrine()->getManager();
        //1-preparation de notre objet
        $modele=$em->getRepository(Promotion::class)->find($id);
        //3-preparation de notre ofrm
        $form=$this->createForm(PromotionType::class,$modele);
        //5-recuperation de donne de formulaire de base de donne
        $form=$form->handleRequest($request);
        //6-validation formulaire
        if ($form->isValid())
        {
            $aa=$modele->getPrixInitiale();
            $c=$modele->getPourcentage();
            $a=$aa*$c;
            $f=$a/100;
            $aaa=$aa-$f;
$modele->setPrixPromo($aaa);
                //7-update 11dans base de donnee
                $em->flush();
                //8-redirection
                return $this->redirectToRoute('promotion');

        }
        //4-envoi form a utilisateur
        return $this->render('@Promo/Default/mod.html.twig',array(
        'form'=>$form->createView()
    ));





    }






    public function createAction(Request $request){



        $Voiture=new Promotion();
        $form=$this->createForm(PromotionType::class,$Voiture);

        $form=$form->handleRequest($request);
        if($form->isSubmitted())
        {


            $az=$this->getDoctrine()->getRepository(Promotion::class);

            $verif=$Voiture->getProduit();

                $em = $this->getDoctrine()->getManager();
                $pourcentage = $Voiture->getPourcentage();
                $produit = $Voiture->getProduit();
                $id = $Voiture->getProduit()->getId();
                $prix = $produit->getPrix();
                $Voiture->setNomPromotion($produit->getLibelle());
                $produit->setPromotion(1);
                $prixP = $prix - (($prix * $pourcentage) / 100);
                $Voiture->setPrixInitiale($prix);
                $Voiture->setPrixPromo($prixP);

                $em->persist($Voiture, $produit);
                $em->flush();
                return $this->redirectToRoute('promotion');


        }
        return $this->render("@Promo/Default/ajout.html.twig",array("form"=>$form->createView()));

    }

    public function updateAction($id)
    {


        $em=$this->getDoctrine()->getManager();
        $Produitt=$em->getRepository(Produit::class)->findById($id);
        $Produitt[3]=1;
        $em->persist($Produitt);
        $em->flush();


    }





    public function supprimerAction($id,Request $request)
    {
        $Candidat=new Promotion();
        $em=$this->getDoctrine()->getManager();
        $Candidat=$em->getRepository(Promotion::class)->find($id);
        $x=$this->getDoctrine()->getRepository(Promotion::class);
        $xx=$x->computeDQLL();
        $em->remove($Candidat);
        $em->flush();
        return $this->redirectToRoute('promotion');
    }

    public function AfficherProduitAction(Request $request)
    {
        $date=new \DateTime();
        $em=$this->getDoctrine()->getRepository(Promotion::class);
        //Now we have the access tocomputeDQL method
        $n=$em->computeDQL($date);
        $n=$em->computeDQLL();
        $d=$this->getDoctrine()->getRepository(Promotion::class)->findAll();
        /**
         * @var $paginator \Knp\Component\Pager\Paginator
         */
        $paginator= $this->get('knp_paginator');
        $rs=$paginator->paginate(
            $d,
            $request->query->getInt('page',1),
            $request->query->getInt('limit',6)



        );
        return $this->render('@Promo/Default/affiche.html.twig', array("d"=>$rs
        ,"s"=>$date));
    }



    public function statAction()
    {
        $pieChart = new PieChart();
        $em= $this->getDoctrine();
        $promoo = $em->getRepository(Promotion::class)->findAll();

 $data= array();
 $stat=['classe', 'nbEtudiant'];
 array_push($data,$stat);
 $w = $em->getRepository(Promotion::class);
        $w = $em->getRepository(Promotion::class);
        $gh=$w->countPDQL();
        $ghh = implode("", $gh[0]);

        for($i=0;$i<$ghh;$i++) {
            $r = $w->yearDQL();
     $nbree = implode("", $r[$i]);
$aze=$w->counntDQL($nbree);
     //$nbreee = implode("", $aze[$i]);






     $g=$w->coounntDQL();
     $aze=$w->counntDQL($g[$i]);
     //$nbreee = implode("", $aze[0]);

     $azee = implode("", $aze[0]);

$azee=$azee*100;
     $stat = array();
     $stat = [$nbree, $azee];

     array_push($data, $stat);

 }
 $pieChart->getData()->setArrayToDataTable(
     $data
 );
 $pieChart->getOptions()->setTitle('Pourcentages des étudiants par niveau');
 $pieChart->getOptions()->setHeight(500);
 $pieChart->getOptions()->setWidth(900);
 $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
 $pieChart->getOptions()->getTitleTextStyle()->setColor('#009900');
 $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
 $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
 $pieChart->getOptions()->getTitleTextStyle()->setFontSize(20);
        return $this->render('@Promo/Default/stat.html.twig', array('piechart' => $pieChart));
    }










}