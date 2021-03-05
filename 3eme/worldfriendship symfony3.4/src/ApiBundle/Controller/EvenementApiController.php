<?php

namespace ApiBundle\Controller;

use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Response;
use UserBundle\Entity\CommentaireEvenement;
use UserBundle\Entity\Reservation;
use PromoBundle\Entity\Promotion;
use UserBundle\Entity\User;
use UserBundle\Entity\Evenement;
use Symfony\Component\Config\Definition\Exception\Exception;


class EvenementApiController extends Controller
{

    public function ajouterEvenementAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $idUtilisateur = $request->get("idUtilisateur");
        $Utilisateur = new User();
        $Utilisateur = $em->getRepository("UserBundle:User")->find($idUtilisateur);
        $idUtilisateur = $request->get("idUtilisateur");
        $nomEvenement = $request->get("nomEvenement");
        $typeEvenement = $request->get("typeEvenement");
        $dateDebutEvenement = new \DateTime(urldecode($request->get("dateDebutEvenement")));
        $dateFinEvenement = new \DateTime(urldecode($request->get("dateFinEvenement")));
        $capaciteEvenement = $request->get("capaciteEvenement");
        $lieuEvenement = $request->get("lieuEvenement");
        $descriptionEvenement = $request->get("descriptionEvenement");
        if ($request->files->get("afficheEvenement") != null) {
            $file = $request->files->get("afficheEvenement");
            $fileName = $file->getClientOriginalName();
            $file->move(
                $this->getParameter('event_photo'),
                $fileName
            );
            $file = $request->files->get("afficheEvenement");
        }
        $evenement = new Evenement();
        $evenement->setNomEvenement(urldecode($nomEvenement));
        $evenement->setTypeEvenement(urldecode($typeEvenement));

        $evenement->setDateDebutEvenement($dateDebutEvenement);
        $evenement->setDateFinEvenement($dateFinEvenement);

        $evenement->setLieuEvenement(urldecode($lieuEvenement));
        $evenement->setCapaciteEvenement($capaciteEvenement);
        $evenement->setDescriptionEvenement(urldecode($descriptionEvenement));
        $evenement->setAffiche(urldecode($fileName));
        $evenement->setIdUser($Utilisateur);

        try {
            $em->persist($evenement);
            $em->flush();
            return new Response("success");

        } catch (Exception $ex) {
            return new Response("fail");
        }

    }


    public function modifierEvenementAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $idEvenement = $request->get("id");
        $nomEvenement = $request->get("nomEvenement");
        $typeEvenement = $request->get("typeEvenement");
        $dateDebutEvenement = new \DateTime(urldecode($request->get("dateDebutEvenement")));
        $dateFinEvenement = new \DateTime(urldecode($request->get("dateFinEvenement")));
        $capaciteEvenement = $request->get("capaciteEvenement");
        $lieuEvenement = $request->get("lieuEvenement");
        $descriptionEvenement = $request->get("descriptionEvenement");
        if ($request->files->get("afficheEvenement") != null) {
            $file = $request->files->get("afficheEvenement");
            $fileName = $file->getClientOriginalName();
            $file->move(
                $this->getParameter('event_photo'),
                $fileName
            );
            $file = $request->files->get("afficheEvenement");
        }
        $evenement = $em->getRepository("UserBundle:Evenement")->find($idEvenement);
        $evenement->setNomEvenement(urldecode($nomEvenement));
        $evenement->setTypeEvenement(urldecode($typeEvenement));
        $evenement->setDateDebutEvenement($dateDebutEvenement);
        $evenement->setDateFinEvenement($dateFinEvenement);
        $evenement->setLieuEvenement(urldecode($lieuEvenement));
        $evenement->setCapaciteEvenement($capaciteEvenement);
        $evenement->setDescriptionEvenement(urldecode($descriptionEvenement));
        $evenement->setAffiche(urldecode($fileName));


        try {
            $em->persist($evenement);
            $em->flush();
            return new Response("success");

        } catch (Exception $ex) {
            return new Response("fail");
        }

    }

    public function TsEvenementAction()
    {
        $em = $this->getDoctrine()->getManager()->getRepository("UserBundle:Evenement")->findAll();


        if ($em) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($em);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }

    }


    public function mesEvenementsAction(Request $request)
    {
        $em = $this->getDoctrine()->getEntityManager();
        $id = $request->get("id");
        $user = new User();
        $user = $em->getRepository('UserBundle:User')->find($id);
        $events = $em->getRepository('UserBundle:Evenement')->findBy(["idUser" => $user]);

        if ($events) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($events);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }
    }


    public function AfficheAction(Request $request)
    {
        $publicResourcesFolderPath = $this->get('kernel')->getRootDir() . '/../web/Evenement/image/affiches/';
        $image = urldecode($request->query->get("img"));

        // This should return the file located in /mySymfonyProject/web/public-resources/TextFile.txt
        // to being viewed in the Browser
        return new BinaryFileResponse($publicResourcesFolderPath . $image);
    }


    public function chercherEvenementIdAction(Request $request)
    {
        $id = $request->query->get("id");
        $em = $this->getDoctrine()->getEntityManager();
        $events = $em->getRepository('UserBundle:Evenement')->find($id);

        if ($events) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($events);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }

    }


    public function supprimerEvenementAction(Request $request)
    {
        $id = $request->get("id");
        $em = $this->getDoctrine()->getManager();
        $event = $em->getRepository("UserBundle:Evenement")->find($id);

        try {
            $em->remove($event);
            $em->flush();
            return new Response("success");

        } catch (Exception $ex) {
            return new Response("fail");
        }
    }


    public function mesReservationsAction(Request $request)
    {
        $em = $this->getDoctrine()->getEntityManager();
        $idUtilisateur = $request->get("idUser");
        $user = new User();
        $user = $em->getRepository('UserBundle:User')->find($idUtilisateur);
        $reservations = $em->getRepository('UserBundle:Reservation')->findBy(["idUser" => $user, "etat" => "Confirmé"]);
        $events = new ArrayCollection();
        foreach ($reservations as $reservation) {
            // getIdEvenement object evenement
            $event = $reservation->getIdEvenement();
            $events->add($event);
        }


        if ($events) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($events);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }
    }


    public function reserverEvenementAction(Request $request)
    {
        $reservation = new Reservation();
        $em = $this->getDoctrine()->getManager();
        $idUtilisateur = $request->get("idUser");
        $idEvenement = $request->get("idEvenement");
        $user = new User();
        $user = $em->getRepository('UserBundle:User')->find($idUtilisateur);
        $event = $em->getRepository('UserBundle:Evenement')->find($idEvenement);
        $reservation->setIdUser($user);
        $reservation->setIdEvenement($event);


        try {
            $reservation->setEtat("Confirmé");
            $event->setCapaciteEvenement($event->getCapaciteEvenement() - 1);
            $event->addReservation($reservation);
            $em->persist($reservation);
            $em->flush();
            return new Response("Success");
        } catch (Exception $ex) {
            return new Response("fail");
        }


    }


    public function annulerReservationAction(Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $idUtilisateur = $request->get("idUser");
        $idEvenement = $request->get("idEvenement");
        $user = new User();
        $user = $em->getRepository('UserBundle:User')->find($idUtilisateur);
        $event = $em->getRepository('UserBundle:Evenement')->find($idEvenement);



        try {

            $event->setCapaciteEvenement($event->getCapaciteEvenement() + 1);

            $query = $em->createQuery(
                'DELETE FROM UserBundle:Reservation res WHERE res.idUser = :idUtilisateur and res.idEvenement = :idEvenement')
                ->setParameter('idUtilisateur',$idUtilisateur)
                ->setParameter('idEvenement',$idEvenement);

            $query->execute();
            $em->flush();
            return new Response("Success");
        } catch (Exception $ex) {
            return new Response("fail");
        }


    }

    public function loginAction(Request $request)
    {
        $username = $request->query->get("username");
        $password = $request->query->get("password");
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository("UserBundle:User")->findOneBy(['username' => $username]);
        $user->setPlainPassword($user->getPlainPassword());
        if ($user) {
            if (password_verify($password, $user->getPassword())) {
                $serializer = new Serializer([new ObjectNormalizer()]);
                $formatted = $serializer->normalize($user);
                return new JsonResponse($formatted);
            } else {
                return new Response("failed");
            }
        } else {
            return new Response("failed");
        }

    }


    public function ajouterCommentaireAction(Request $request)
    {
        $commentaire = new CommentaireEvenement();
        $em = $this->getDoctrine()->getManager();
        $idUtilisateur = $request->get("idUser");
        $idEvenement = $request->get("idEvenement");
        $contenu = $request->get("contenu");
        $user = new User();
        $user = $em->getRepository('UserBundle:User')->find($idUtilisateur);
        $event = $em->getRepository('UserBundle:Evenement')->find($idEvenement);
        $commentaire->setIdUser($user);
        $commentaire->setIdEvenement($event);
        $commentaire->setContenu($contenu);

        try {
            $event->addCommentaire($commentaire);
            $em->persist($commentaire);
            $em->flush();
            return new Response("Success");
        } catch (Exception $ex) {
            return new Response("fail");
        }
    }

    public function afficherCommentaireAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $idEvenement = $request->get("idEvenement");
        $commentaires  = $em->getRepository('UserBundle:CommentaireEvenement')->findBy(["idEvenement" => $idEvenement]);
        if ($commentaires) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($commentaires);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }
    }



    public function evenementAutoReservationAction()
    {

        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery("
           SELECT e.nomEvenement FROM UserBundle:Evenement e ORDER BY e.id DESC
            ")->setMaxResults(1);
        $events = $query->getResult();

        if ($events) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($events);
            return new JsonResponse($formatted);
        } else {
            return new Response("no data");
        }

    }
    // marweeeeeeeeeeeeeeeen



    public function findAction($email){
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ClientBundle:User')
            ->findOneBy(array('email'=>$email));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tasks);
        return new JsonResponse($formatted);
    }

    public function newAction(Request $request){
        $em = $this->getDoctrine()->getManager();
        $user = new User();
        $user -> setUsername($request->get('username'));
        $user -> setEmail($request->get('email'));
        $MotDepasse = $request->get('password');
        $MotDePassHach = password_hash($MotDepasse,PASSWORD_DEFAULT);
        $user -> setPassword($MotDePassHach);
        $user -> setRoles(array($request->get('role')));


        $em->persist($user);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }
    public function editAction(Request $request,$id){
        $em = $this->getDoctrine()->getManager();
        $user=$em->getRepository("ClientBundle:User")->find($id);
        $user -> setUsername($request->get('nom'));
        $user -> setEmail($request->get('email'));
        $user -> setPassword($request->get('password'));
        $user -> setNom($request->get('nom'));
        $user -> setPrenom($request->get('prenom'));
        $em->persist($user);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }


    public function AfficheMobileEvenementAction()
    {
        $em=$this->getDoctrine()->getManager();
        $tasks = $em->getRepository(Promotion::class)->findall();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($tasks);

        return new JsonResponse($data);
    }





}


// bech n5arej text mel json lezem parceur ( json parcer )