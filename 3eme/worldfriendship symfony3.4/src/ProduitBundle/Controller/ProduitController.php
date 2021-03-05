<?php

namespace ProduitBundle\Controller;

use ProduitBundle\Entity\Produit;
use ProduitBundle\Entity\CategorieProduit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Produit controller.
 *
 */
class ProduitController extends Controller
{
    /**
     * Lists all gestionproduit entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $produits = $em->getRepository('ProduitBundle:Produit')->findByOrder();
        $randproduits = $em->getRepository('ProduitBundle:Produit')->getRandom();
        $categorie = $em->getRepository('ProduitBundle:CategorieProduit')->findAll();


        return $this->render('@Produit/index.html.twig', array(
            'produits' => $produits,'categorie' => $categorie,'randproduits'=>$randproduits
        ));
    }

    public function categorieAction(Request $request,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $categorie =  $em->getRepository('ProduitBundle:CategorieProduit')->find($id);
        $produits = $em->getRepository('ProduitBundle:Produit')->findByCategorie($id);

        $paginator = $this->get('knp_paginator');
        $pagination = $paginator->paginate(
            $produits,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 4)
        );
        return $this->render('@Produit/categorie.html.twig',array('produits'=>$pagination,'categorie'=>$categorie));
    }

    public function produitAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($id);

        return $this->render('@Produit/product.html.twig', array('produit' => $produit ));
    }
    public function searchAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $name = $request->get('n');
        $key = $request->get('q');

        if($name=='categorie'){
            $code =  $request->get('c');
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getCategorieProduct($key,$code);
        }elseif ($name=='souscategorie'){
            $code =  $request->get('c');
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getSousCategorieProduct($key,$code);
        }else {
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getProduct($key);
        }

        $paginator = $this->get('knp_paginator');
        $pagination = $paginator->paginate(
            $searchProduct,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 4)
        );

        return $this->render('@Produit/search.html.twig',array('produits'=>$pagination,'q'=>$key));
    }

    public function searchProduitAjaxAction(Request $request){


        $em = $this->getDoctrine()->getManager();

        $name = $request->request->get('name');
        $key = $request->request->get('key');

        if($name=='categorie'){
            $code =  $request->request->get('code');
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getCategorieProduct($key,$code);
        }elseif ($name=='souscategorie'){
            $code =  $request->request->get('code');
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getSousCategorieProduct($key,$code);
        }else {
            $searchProduct = $em->getRepository('ProduitBundle:Produit')->getProduct($key);
        }

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($searchProduct);

        return new JsonResponse($data);

    }
    public function getproduitAjaxAction(Request $request){

        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();

        $produit = $em->getRepository('ProduitBundle:Produit')->find($id);

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($produit);

        return new JsonResponse($data);
    }

    public function panierAction()
    {
        return $this->render('@Produit/panier.html.twig');

    }
    public function wishlistAction()
    {
        $user = $this->getUser();

        $em = $this->getDoctrine()->getManager();

        $likes = $em->getRepository('ProduitBundle:LikeProduit')->findBy(array('user'=>$user));

        return $this->render('@Produit/wishlist.html.twig',array('likes'=>$likes));
    }


    public function getBestSellerProduitAction(){

        $em = $this->getDoctrine()->getManager();
        $produits = $em->getRepository('ProduitBundle:Produit')->getBestSeller();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($produits);

        return new JsonResponse($data);
    }

    public function topProduitAction(){

        $em = $this->getDoctrine()->getManager();
        $likes = $em->getRepository('ProduitBundle:LikeProduit')->getTopProduct();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($likes);

        return new JsonResponse($data);
    }


}
