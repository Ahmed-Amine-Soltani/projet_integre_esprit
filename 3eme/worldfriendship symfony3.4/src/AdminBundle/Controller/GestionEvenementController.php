<?php

namespace AdminBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\EvenementRepository;
use UserBundle\Entity\Evenement;


class GestionEvenementController extends Controller
{
    public function afficherEvenementAdminAction(){
        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository("UserBundle:Evenement")->findAll();
        return $this->render('@Admin/gestion_Evenement/admin_evenement.html.twig', ['evenements' => $evenements ]);
    }
    public function findWeekAction()
    {
        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository("UserBundle:Evenement")->findWeek();
        return $this->render('@Admin/gestion_Evenement/admin_evenement_semaine.html.twig', ['evenements' => $evenements ]);
    }

    public function supprimerEvenementAdminAction(Request $request){
        $user = $this->getUser();
        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('UserBundle:Evenement')->find($id);
        if ($user !== null) {
            $em->remove($evenement);
            $em->flush();
            $this->addFlash(
                'success-sup-ad',
                'Supression avec succès!'
            );
        }
        return $this->redirectToRoute('admin_evenement');
    }
}
