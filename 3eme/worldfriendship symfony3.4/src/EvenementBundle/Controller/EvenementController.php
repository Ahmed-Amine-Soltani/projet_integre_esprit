<?php

namespace EvenementBundle\Controller;

use Endroid\QrCode\QrCode;
use Swift_Attachment;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Console\Output\ConsoleOutput;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Validator\Constraints\Date;
use Symfony\Component\Validator\Constraints\DateTime;
use UserBundle\Entity\CommentaireEvenement;
use UserBundle\Entity\Evenement;
use EvenementBundle\Form\AjouterEvenement;
use UserBundle\Entity\User;
use UserBundle\Entity\Reservation;
use UserBundle\Entity\EvenementRepository;
use EvenementBundle\Form\ModifierEvenement;
use EvenementBundle\Form\ReservationForm;
use EvenementBundle\Form\CommentaireForm;


class EvenementController extends Controller
{
    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|\Symfony\Component\HttpFoundation\Response
     */

    public function ajouterEvenementAction(Request $request)
    {
        $user = $this->getUser();
        $event = new Evenement();
        $form = $this->createForm(AjouterEvenement::class, $event);
        $form->handleRequest($request);
        if ($user !== null) {
            if ($form->isValid()) {
                $event->setEtatEvenement("Non");
                $event->setIdUser($user);
                $date1 = date_create($event->getDateDebutEvenement()->format('Y-m-d'));
                $date2 = date_create($event->getDateFinEvenement()->format('Y-m-d'));
                $diff = date_diff($date2, $date1);
                $event->setDureeEvenement($diff->format('%m mois et %d jours '));
                $em = $this->getDoctrine()->getManager();
                $em->persist($event);
                $em->flush();
                return $this->redirectToRoute('accueil_evenement');
            }
            return $this->render('@Evenement/Evenement_Views/ajouter_evenement.html.twig', ['evenements' => null, 'ajouter_form' => $form->createView(), 'tag' => 'Ajouter un événement']);
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    public function modifierEvenementAction(Request $request)
    {
        $user = $this->getUser();

        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('UserBundle:Evenement')->find($id);
        $form = $this->createForm(ModifierEvenement::class, $evenement);
        $form->handleRequest($request);
        if ($user !== null) {
            if ($form->isValid()) {
                $em->persist($evenement);
                $em->flush();
                $this->addFlash(
                    'success-sup-modif',
                    'Modification avec succès!'
                );
                return $this->redirectToRoute('afficher_mes_evenements');
            }
        } else {
            return $this->redirectToRoute('fos_user_registration_register');
        }
        return $this->render('@Evenement/Evenement_Views/modifier_evenement.html.twig', ['evenements' => null, 'modifier_form' => $form->createView(), 'tag' => 'Modifier un événement']);
    }

    public function supprimerEvenementAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('UserBundle:Evenement')->find($id);
        if ($user !== null) {
            $em->remove($evenement);
            $em->flush();
            $this->addFlash(
                'success-sup-modif',
                'Supression avec succès!'
            );
        }
        return $this->redirectToRoute('afficher_mes_evenements');
    }

    public function detailsEvenementAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');


        $reservation = new Reservation();
        $formRes = $this->createForm(ReservationForm::class, $reservation);
        $formRes->handleRequest($request);

        $commentaire = new CommentaireEvenement();
        $formCom = $this->createForm(CommentaireForm::class, $commentaire);
        $formCom->handleRequest($request);

        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository('UserBundle:Evenement')->find($id);

        $check = count($em->getRepository('UserBundle:Reservation')->findBy(['idEvenement' => $evenements,
            'idUser' => $user, 'etat' => 'Confirmé']));
        //  $dispo = $evenements->getCapaciteEvenement() - count($em->getRepository('UserBundle:Reservation')->findBy(["idEvenement" => $evenements, "etatEvenement" => "Confirmé"]));

        if ($user !== null) {
            if ($formRes->isValid() && $request->request->has($formRes->getName())) {
                $numeroTicket = count($em->getRepository('UserBundle:Reservation')->findBy(["idEvenement" => $evenements, "etat" => "Confirmé"]));
                $reservation->setIdUser($user);
                $reservation->setIdEvenement($evenements);
                $reservation->setTypeReservation($evenements->getTypeReservation());
                if ($evenements->getTypeReservation() == "Payante") {
                    $reservation->setTarif($evenements->getPrixEvenement());
                }
                if ($numeroTicket) {
                    $reservation->setNumeroTicket($numeroTicket + 1);
                } else {
                    $reservation->setNumeroTicket(1);
                }
                $reservation->setEtat("Confirmé");

                $evenements->addReservation($reservation);
                $evenements->setCapaciteEvenement($evenements->getCapaciteEvenement() - 1);
                $em->persist($reservation);
                $em->flush();
                return $this->redirectToRoute('afficher_mes_reservation');
            }

            // Partie commentaires
            if ($formCom->isValid() && $request->request->has($formCom->getName())) {

                $commentaire->setIdEvenement($evenements);
                $commentaire->setIdUser($user);
                $commentaire->setEtatCommentaire("OK");
                $evenements->addCommentaire($commentaire);
                $em->persist($commentaire);
                $em->flush();
            }
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }

        return $this->render('@Evenement/Evenement_Views/details_evenement.html.twig',
            ['evenements' => $evenements, 'tag' => 'détails événement', 'formRes' => $formRes->createView(), 'check' => $check
                , 'formCom' => $formCom->createView(),]);
    }


    public function afficherEvenementAction()
    {
        $user = $this->getUser();
        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository("UserBundle:Evenement")->findAll();

        // pour récuperer l'email d'utilisateur connecté
        $users = $em->getRepository("UserBundle:User")->findBy(array('id' => $user), array());
        $data = ['users' => $users, 'evenements' => $evenements];
        //  return $this->render('@Evenement/Evenement_Views/accueil_evenement.html.twig',['data_evenement' => json_encode($data)]);
        return $this->render('@Evenement/Evenement_Views/accueil_evenement.html.twig', ['tag' => null, 'users' => $users, 'evenements' => $evenements]);
    }

    /*
    public function afficherMesEvenementAction()
    {
        $user = $this->getUser();
        if ($user !== null) {

            $em = $this->getDoctrine()->getManager();
            $evenements = $em->getRepository("UserBundle:Evenement")->findBy(array('idUser' => $user), array());

            return $this->render('@Evenement/Evenement_Views/afficher_mes_evenements.html.twig', ['tag' => "Mes Evénements", 'evenements' => $evenements]);
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }

    }
*/


    public function autreEvenementAction()
    {
        return $this->render('@Evenement/Evenement_Views/autre_evenement.html.twig', ['tag' => "Autre Evenement"]);
    }

    public function envoyerTicketAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('UserBundle:Evenement')->find($id);
        $usermail = $em->getRepository('UserBundle:User')->getMailUser($user->getId());

        $qrCode = new QrCode('Bonjour Monsieur : ' . $user . ' , ceci votre ticket vous avez participé à l\'événement : ' . $evenement->getNomEvenement() .
            ' , Date debut : ' . $evenement->getDateDebutEvenement()->format('Y-m-d'));
        header('Content-Type: image/png');
        $qrCode->writeFile('Evenement/image/qrcode/qrcode.png');

        //send tiquet at mail
        $user = $this->getUser();
        $message = (new \Swift_Message())->setSubject('Bonjour Monsieur ' . $user)
            ->setFrom('soltani.ahmed1994@gmail.com')
            ->setTo('soltani.ahmed1994@gmail.com')
            ->setBody('votre ticket ')
            ->attach(Swift_Attachment::fromPath('Evenement/image/qrcode/qrcode.png')
                ->setDisposition('inline'));
        $this->get('mailer')->send($message);
        $this->addFlash(
            'success',
            'votre QRCODE est envoyé avec succès sur votre mail : '
        );
        return $this->redirectToRoute('accueil_evenement');
    }

    public function pdfAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('UserBundle:Evenement')->find($id);
        $snappy = $this->get('knp_snappy.pdf');

        $html = "<h1 style=\"background-color:powderblue;\" align='center'> Evenement : " . $evenement->getNomEvenement() .
            "  </h1> <h1> Bonjour Monsieur  : " . $user . " </h1>  <p> cet evenement dura " . $evenement->getDureeEvenement() . "</p>";
        $filename = 'pdf ' . $evenement->getNomEvenement();

        return new Response(
            $snappy->getOutputFromHtml($html),
            200,
            array(
                'Content-Type' => 'application/pdf',
                'Content-Disposition' => 'attachment; filename="' . $filename . '.pdf"'
            )
        );
    }

    public function mesEvenementsDonneesAction(Request $request)
    {

        $length = $request->get('length');
        $length = $length && ($length != -1) ? $length : 0;

        $start = $request->get('start');
        $start = $length ? ($start && ($start != -1) ? $start : 0) / $length : 0;

        $search = $request->get('search');

        $user = $this->getUser();
        $filters = [
            'query' => @$search['value'],
            'user' => @$user,
        ];

        $evenements = $this->getDoctrine()->getRepository('UserBundle:Evenement')->search(
            $filters, $start, $length
        );

        $output = array(
            'data' => array(),
            'recordsFiltered' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->search($filters, 0, false)),
            'recordsTotal' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->search(array(), 0, false))
        );

        foreach ($evenements as $evenement) {

            $output['data'][] = [
                'nomEvenement' => $evenement->getNomEvenement(),
                'typeEvenement' => $evenement->getTypeEvenement(),
                'capaciteEvenement' => $evenement->getCapaciteEvenement(),
                'typeReservation' => $evenement->getTypeReservation(),
                'prixEvenement' => $evenement->getPrixEvenement(),
                'dateDebutEvenement' => $evenement->getDateDebutEvenement()->format('Y-m-d H:i'),
                'dureeEvenement' => $evenement->getDureeEvenement(),
                'lieuEvenement' => $evenement->getLieuEvenement(),
                'Affiche' => '<img class="resize" src="../Evenement/image/affiches/' . $evenement->getAffiche() . '"/>',
                'Action' => "<a href=" . $this->generateUrl('details_evenement', ['id' => $evenement->getId()]) . " target=\"_blank\"><img src=\"https:/img.icons8.com/ios/26/000000/show-property.png\"> </a>
              <a href=" . $this->generateUrl('modifier_evenement', ['id' => $evenement->getId()]) . " target=\"_blank\"><img src=\"https:/img.icons8.com/ios-glyphs/20/000000/services.png\"> </a>
                <a href=" . $this->generateUrl('supprimer_evenement', ['id' => $evenement->getId()]) . "><img src=\"https:/img.icons8.com/material/26/000000/trash.png\"></a>"
            ];

        }
        return new Response(json_encode($output), 200, ['Content-Type' => 'application/json']);
    }

    public function mesEvenementAction()
    {
        $user = $this->getUser();
        if ($user !== null) {
            return $this->render('@Evenement/Evenement_Views/mes_evenements.html.twig', []);
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    public function mesReservationDonneesAction(Request $request)
    {
        $length = $request->get('length');
        $length = $length && ($length != -1) ? $length : 0;

        $start = $request->get('start');
        $start = $length ? ($start && ($start != -1) ? $start : 0) / $length : 0;

        $search = $request->get('search');

        $user = $this->getUser();
        $filters = [
            'query' => @$search['value'],
            'user' => @$user,
        ];
        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository("UserBundle:Evenement")->searchReservation(
            $filters, $start, $length
        );


        $output = array(
            'data' => array(),
            'recordsFiltered' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->searchReservation($filters, 0, false)),
            'recordsTotal' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->searchReservation(array(), 0, false))
        );
        foreach ($evenements as $event) {
            $reservations = $event->getReservations();
            foreach ($reservations as $reservation) {
                if ($reservation->getIdUser() == $user && $reservation->getEtat() == "Confirmé") {

                    $output['data'][] = [
                        'nomEvenement' => $event->getNomEvenement(),
                        'etat' => $reservation->getEtat(),
                        'typeEvenement' => $event->getTypeEvenement(),
                        'capaciteEvenement' => $event->getCapaciteEvenement(),
                        'typeReservation' => $event->getTypeReservation(),
                        'prixEvenement' => $event->getPrixEvenement(),
                        'dateDebutEvenement' => $event->getDateDebutEvenement()->format('Y-m-d H:i'),
                        'dureeEvenement' => $event->getDureeEvenement(),
                        'lieuEvenement' => $event->getLieuEvenement(),
                        'Affiche' => '<img class="resize" src="../Evenement/image/affiches/' . $event->getAffiche() . '"/>',
                        'Action' => "<a href=" . $this->generateUrl('annuler_reservation', ['id' => $reservation->getIdReservation(), 'id-event' => $event->getId()]) . ">annuler</a>"
                    ];
                } else if ($reservation->getIdUser() == $user && $reservation->getEtat() == "Annulé") {

                    $output['data'][] = [
                        'nomEvenement' => $event->getNomEvenement(),
                        'etat' => $reservation->getEtat(),
                        'typeEvenement' => $event->getTypeEvenement(),
                        'capaciteEvenement' => $event->getCapaciteEvenement(),
                        'typeReservation' => $event->getTypeReservation(),
                        'prixEvenement' => $event->getPrixEvenement(),
                        'dateDebutEvenement' => $event->getDateDebutEvenement()->format('Y-m-d H:i'),
                        'dureeEvenement' => $event->getDureeEvenement(),
                        'lieuEvenement' => $event->getLieuEvenement(),
                        'Affiche' => '<img class="resize" src="../Evenement/image/affiches/' . $event->getAffiche() . '"/>',
                        'Action' => "<a href=" . $this->generateUrl('reconfirmer_reservation', ['id' => $reservation->getIdReservation(), 'id-event' => $event->getId()]) . ">reconfirmer</a>"
                    ];
                }

            }
        }
        return new Response(json_encode($output), 200, ['Content-Type' => 'application/json']);
    }

    public function mesReservationAction()
    {
        $user = $this->getUser();
        if ($user !== null) {
            return $this->render('@Evenement/Evenement_Views/mes_reservations_evenement.html.twig', []);
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }

    }

    public function annulerReservationAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');
        $idevenet = $request->get('id-event');
        $em = $this->getDoctrine()->getManager();
        $reservation = $em->getRepository('UserBundle:Reservation')->find($id);
        $evenements = $em->getRepository('UserBundle:Evenement')->find($idevenet);
        if ($user !== null && $reservation->getIdUser() == $user) {
            $evenements->setCapaciteEvenement($evenements->getCapaciteEvenement() + 1);
            $reservation->setEtat("Annulé");
            $em->persist($reservation);
            $em->flush();
            $this->addFlash(
                'success_res',
                'Annulation avec succès!'
            );
        }

        return $this->render('@Evenement/Evenement_Views/mes_reservations_evenement.html.twig');
    }

    public function reconfirmerReservationAction(Request $request)
    {
        $user = $this->getUser();
        $id = $request->get('id');
        $idevenet = $request->get('id-event');
        $em = $this->getDoctrine()->getManager();
        $reservation = $em->getRepository('UserBundle:Reservation')->find($id);
        $evenements = $em->getRepository('UserBundle:Evenement')->find($idevenet);
        if ($user !== null && $reservation->getIdUser() == $user) {
            $evenements->setCapaciteEvenement($evenements->getCapaciteEvenement() - 1);
            $reservation->setEtat("Confirmé");
            $em->persist($reservation);
            $em->flush();
            $this->addFlash(
                'success_res',
                'Reconfirmation avec succès!'
            );
        }

        return $this->render('@Evenement/Evenement_Views/mes_reservations_evenement.html.twig');
    }


    public function testAction(Request $request)
    {
        $length = $request->get('length');
        $length = $length && ($length != -1) ? $length : 0;

        $start = $request->get('start');
        $start = $length ? ($start && ($start != -1) ? $start : 0) / $length : 0;

        $search = $request->get('search');

        $user = new User();
        $user = $this->getUser();
        $filters = [
            'query' => @$search['value'],
            'user' => @$user,
        ];

        $em = $this->getDoctrine()->getManager();
        $evenements = $em->getRepository("UserBundle:Evenement")->findAll();

        // pour récuperer l'email d'utilisateur connecté
        $users = $em->getRepository("UserBundle:User")->findBy(array('id' => $user), array());
        // $data = ['users'=> $users , 'evenements' => $evenements];

        $output = array(
            'data' => array(),
        );
        foreach ($evenements as $event) {

            $output['data'][] = [
                'nomEvenement' => $event->getNomEvenement(),
                'typeEvenement' => $event->getTypeEvenement(),
                'capaciteEvenement' => $event->getCapaciteEvenement(),
                'typeReservation' => $event->getTypeReservation(),
                'prixEvenement' => $event->getPrixEvenement(),
                'dateDebutEvenement' => $event->getDateDebutEvenement()->format('Y-m-d H:i'),
                'dureeEvenement' => $event->getDureeEvenement(),
                'lieuEvenement' => $event->getLieuEvenement(),
                'Affiche' => '<img class="resize" src="../Evenement/image/affiches/' . $event->getAffiche() . '"/>',
                'Details' => "<a href=" . $this->generateUrl('details_evenement', ['id' => $event->getID()]) . " target=\"_blank\"><img src=\"https://img.icons8.com/ios/26/000000/show-property.png\"> </a>",
                'Modifier' => "<a href=" . $this->generateUrl('modifier_evenement', ['id' => $event->getID()]) . " target=\"_blank\"><img src=\"https://img.icons8.com/ios-glyphs/20/000000/services.png\"> </a>",
                'Supprimer' => "<a href=" . $this->generateUrl('supprimer_evenement', ['id' => $event->getID()]) . " target=\"_blank\"><img src=\"https://img.icons8.com/material/26/000000/trash.png\"></a>"
            ];
        }
        return new Response(json_encode($output), 200, ['Content-Type' => 'application/json']);

        //  return $this->render('@Evenement/Evenement_Views/test.html.twig',['data_evenement' => json_encode($output)]);
        // return  $this->render('@Evenement/Evenement_Views/test.html.twig', ['evenements' => $output]);
        // return new Response("hello world");
    }


    /* public function testAction(Request $request)
        {
            $length = $request->get('length');
            $length = $length && ($length != -1) ? $length : 0;

            $start = $request->get('start');
            $start = $length ? ($start && ($start != -1) ? $start : 0) / $length : 0;

            $search = $request->get('search');

            $user = $this->getUser();
            $filters = [
                'query' => @$search['value'],
                'user' => @$user,
            ];

            $evenements = $this->getDoctrine()->getRepository('UserBundle:Evenement')->search(
                $filters, $start, $length
            );

            $output = array(
                'data' => array(),
                'recordsFiltered' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->search($filters, 0, false)),
                'recordsTotal' => count($this->getDoctrine()->getRepository('UserBundle:Evenement')->search(array(), 0, false))
            );

            foreach ($evenements as $event) {

                $output['data'][] = [
                    'nomEvenement' => $event->getNomEvenement()
                ];
            }

            return new Response(json_encode($output), 200, ['Content-Type' => 'application/json']);
        }
    */
/////////////////////////////////////////////////////////////////////////////
///

}
