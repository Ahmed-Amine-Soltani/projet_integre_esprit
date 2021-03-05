<?php

namespace ProduitBundle\Controller;

use DateTime;
use ProduitBundle\Entity\lignedepanier;
use ProduitBundle\Entity\Panier;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class PanierController extends Controller
{
    public function createPanierAction(Request $request){


        $user = $this->getUser();

        if(!$user) {
          return new JsonResponse(array('check'=>false));

        }else {

            $em = $this->getDoctrine()->getManager();
            $panier = $em->getRepository('ProduitBundle:Panier')->findOneBy(array('user'=>$user));

            $id = $request->request->get('id');
            $qte = $request->request->get('qte');
            $produit = $em->getRepository('ProduitBundle:Produit')->find($id);
            if(!$qte){
                $qte = 1;
            }


            if($produit->getStock()<$qte){
                return new JsonResponse(array('stock'=>false));
            }


            $produit->setStock($produit->getStock()-$qte);

            if($panier){
                $panier->setNbrProduit($panier->getNbrProduit()+$qte);
                $panier->setTotal(($produit->getPrix()*$qte)+ $panier->getTotal());

            }else {

                $panier = new Panier();
                $panier->setUser($user);
                $panier->setNbrProduit($qte);
                $panier->setTotal($produit->getPrix()+ 7);

            }

            $lignePanier = new lignedepanier();

            $lignePanier->setPanier($panier);
            $lignePanier->setProduit($produit);
            $lignePanier->setQuantite($qte);
            $lignePanier->setCreated(new DateTime());
            $em->persist($panier);
            $em->persist($lignePanier);
            $em->persist($produit);
            $em->flush();


        }


        return new JsonResponse(array('check'=>true));


    }


    public function incrementPanierAjaxAction(Request $request){

        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();

        $lignepanier = $em->getRepository('ProduitBundle:lignedepanier')->find($id);

        $produit_id = $lignepanier->getProduit()->getId();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($produit_id);

        if($produit->getStock()==0){
            return new JsonResponse(false);

        }

        $panier_id = $lignepanier->getPanier()->getId();
        $panier = $em->getRepository('ProduitBundle:Panier')->find($panier_id);

        $produit->setStock($produit->getStock()-1);
        $lignepanier->setQuantite($lignepanier->getQuantite()+1);

        $panier->setNbrProduit($panier->getNbrProduit()+1);
        $panier->setTotal($panier->getTotal()+ $lignepanier->getProduit()->getPrix());

        $em->persist($produit);
        $em->persist($lignepanier);
        $em->persist($panier);
        $em->flush();

        return new JsonResponse();

    }


    public function dicrementPanierAjaxAction(Request $request){

        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();

        $lignepanier = $em->getRepository('ProduitBundle:lignedepanier')->find($id);

        $produit_id = $lignepanier->getProduit()->getId();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($produit_id);

        if($lignepanier->getQuantite()==1){
            return new JsonResponse(false);
        }

        $panier_id = $lignepanier->getPanier()->getId();
        $panier = $em->getRepository('ProduitBundle:Panier')->find($panier_id);

        $produit->setStock($produit->getStock()+1);
        $lignepanier->setQuantite($lignepanier->getQuantite()-1);

        $panier->setNbrProduit($panier->getNbrProduit()-1);
        $panier->setTotal($panier->getTotal()- $lignepanier->getProduit()->getPrix());

        $em->persist($produit);
        $em->persist($lignepanier);
        $em->persist($panier);
        $em->flush();

        return new JsonResponse();

    }


    public function getPanierAjaxAction(){


        $user = $this->getUser();

        $em = $this->getDoctrine()->getManager();
        $panier = $em->getRepository('ProduitBundle:Panier')->findOneBy(array('user'=>$user));

        $normalizer = new ObjectNormalizer();

        $normalizer->setCircularReferenceLimit(2);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($panier);


        return new JsonResponse($data);

    }

    public function deletePanierAjaxAction(Request $request){


        $id = $request->request->get('id');

        $em = $this->getDoctrine()->getManager();
        $lignepanier = $em->getRepository('ProduitBundle:lignedepanier')->find($id);

        $produit_id = $lignepanier->getProduit()->getId();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($produit_id);


        $panier = $em->getRepository('ProduitBundle:Panier')->findOneBy(array('id'=>$lignepanier->getPanier()->getId()));

        $panier->setTotal($panier->getTotal() - ($lignepanier->getProduit()->getPrix() * $lignepanier->getQuantite()));
        $panier->setNbrProduit($panier->getNbrProduit() - $lignepanier->getQuantite());

        $produit->setStock($produit->getStock()+ $lignepanier->getQuantite());

        $em->remove($lignepanier);
        $em->persist($panier);
        $em->persist($produit);
        $em->flush();


        return new JsonResponse();


    }

}
