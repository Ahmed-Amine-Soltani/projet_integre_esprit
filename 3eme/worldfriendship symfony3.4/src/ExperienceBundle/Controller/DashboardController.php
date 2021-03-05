<?php
/**
 * Created by PhpStorm.
 * User: houba
 * Date: 20-Feb-19
 * Time: 3:23 PM
 */

namespace ExperienceBundle\Controller;


use Knp\Bundle\SnappyBundle\Snappy\Response\JpegResponse;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use UserBundle\Entity\Commentaire;
use UserBundle\Entity\Experience;
use UserBundle\Form\CommentaireType;
use UserBundle\Form\ExperienceType;

class DashboardController extends Controller
{

    public function AfficheDashAction(Request $request)
    {
        $exp=$this->getDoctrine()->getRepository(Experience::class)->findAll();

        /**
         * @var  $paginator \Knp\Component\Pager\Paginator
         */

        $paginator=$this->get('knp_paginator');
        $res=$paginator->paginate(
            $exp,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 2)
        );

        //dump(get_class($paginator));

        return $this->render('@Experience/admin.html.twig', array('exp'=>$res));
    }

    public function AfficheDashMobileAction(Request $request)
    {
        $exp=$this->getDoctrine()->getRepository(Experience::class)->findAll();
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);
    }

    public function FindDashMobileAction($id)
    {
        $exp=$this->getDoctrine()->getRepository(Experience::class)->findIdParameter($id);
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);
    }

    Public function SupprimerDashAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $exp=$em->getRepository(Experience::class)->find($id);
        $em->remove($exp);
        $em->flush();
        return $this->redirectToRoute("AfficherDash");
    }

    Public function SupprimerExpMobileAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $exp = $em->getRepository(Experience::class)->find($id);
        $em->remove($exp);
        $em->flush();
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);
    }


    public function PdfAction()
    {
        {
            $pageUrl = $this->generateUrl('AfficherDash', array(), true); // use absolute path!

            return new PdfResponse(
                $this->get('knp_snappy.pdf')->getOutput($pageUrl),
                'file.pdf'
            );
        }
    }


}