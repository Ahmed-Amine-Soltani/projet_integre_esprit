<?php

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class DefaultController extends Controller
{
    /**
     * @Route("/", name="homepage")
     */
    public function indexAction()
    {
        $experience = $this->getDoctrine()->getRepository('UserBundle:Experience')->topExp();
        return $this->render('@Experience/experience.html.twig', array(
            'exp' => $experience,
        ));
    }

    public function index2Action(Request $request)
    {
        // replace this example code with whatever you need
        return $this->render('ExperienceBundle:Default:index.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')).DIRECTORY_SEPARATOR,
        ]);
    }
}
