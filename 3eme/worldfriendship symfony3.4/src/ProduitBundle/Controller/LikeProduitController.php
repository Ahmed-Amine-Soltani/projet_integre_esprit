<?php

namespace ProduitBundle\Controller;

use DateTime;
use ProduitBundle\Entity\LikeProduit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class LikeProduitController extends Controller
{
    public function getlikeproduitAction(){

        $user = $this->getUser();

        $em = $this->getDoctrine()->getManager();
        $likes = $em->getRepository('ProduitBundle:Like')->findBy(array('user'=>$user));

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

    public function likeproduitAction(Request $request){

        $user = $this->getUser();

        if(!$user) {
            return new JsonResponse(array('check'=>false));
        }

        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($id);
        $like = $em->getRepository('ProduitBundle:LikeProduit')->findOneBy(array('produit'=>$produit,'user'=>$user));

        if($like){
            $em->remove($like);
            $em->flush();
            return new JsonResponse('dislike');

        }else {
            $like = new LikeProduit();
            $like->setProduit($produit);
            $like->setUser($user);
            $like->setCreated(new DateTime());

            $em->persist($like);
            $em->flush();

            return new JsonResponse('like');
        }



    }


    public function deletelikeproduitAction($id){


        $em = $this->getDoctrine()->getManager();
        $like = $em->getRepository('ProduitBundle:LikeProduit')->find($id);

        $em->remove($like);
        $em->flush();
        return $this->redirectToRoute('produit_wishlist');
    }
}
