<?php

namespace ProduitBundle\Controller;

use DateTime;
use ProduitBundle\Entity\CategorieProduit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Categorieproduit controller.
 *
 */
class CategorieController extends Controller
{


    public function getCategorieJsonAction()
    {

        $em = $this->getDoctrine()->getManager();

        $categorie = $em->getRepository('ProduitBundle:CategorieProduit')->findAll();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($categorie);


        return new JsonResponse($data);

    }


}
