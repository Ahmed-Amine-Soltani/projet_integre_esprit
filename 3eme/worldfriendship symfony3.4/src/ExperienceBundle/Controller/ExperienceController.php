<?php

namespace ExperienceBundle\Controller;

use ExperienceBundle\ExperienceBundle;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use UserBundle\Entity\Categorie;
use UserBundle\Entity\Commentaire;
use UserBundle\Entity\Experience;
use UserBundle\Form\CommentaireType;
use UserBundle\Form\ExperienceType;
use UserBundle\Entity\User;
use UserBundle\UserBundle;

class ExperienceController extends Controller
{
    public function indexAction()
    {
        $experience = $this->getDoctrine()->getRepository('UserBundle:Experience')->topExp();
        return $this->render('@Experience/experience.html.twig', array(
            'exp' => $experience,
        ));
    }


    public function AfficherAjouterAction(Request $request)
    {


        $user = $this->getUser();
        $experience = new Experience();
        $exp = $this->getDoctrine()->getRepository('UserBundle:Experience')->findAll();
        $cat = $this->getDoctrine()->getRepository('UserBundle:Categorie')->findAll();
        $form = $this->createForm(ExperienceType::class, $experience);
        $form = $form->handleRequest($request);


        /**
         * @var  $paginator \Knp\Component\Pager\Paginator
         */

        $paginator = $this->get('knp_paginator');
        $res = $paginator->paginate(
            $exp,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 6)
        );


        if ($user !== null) {
            $experience->setDateExp(new \DateTime());

            if ($form->isSubmitted()) {
                $experience->setUser($user);
                $em = $this->getDoctrine()->getManager();
                $em->persist($experience);
                $em->flush();

                return $this->redirectToRoute('allPosts', array());
            }
            return $this->render('@Experience/allPosts.html.twig', array(
                'form' => $form->createView(),
                'experience' => $res,
                'categorie' => $cat,
                'user' => $user

            ));
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    public function newAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $task = new Experience();
        $idUtilisateur = $request->get("user");

        $Utilisateur = $em->getRepository("UserBundle:User")->find($idUtilisateur);
        $task->setNomExp($request->get('nomExp'));
        $task->setLieuExp($request->get('lieuExp'));
        $task->setDescExp($request->get('descExp'));
        $task->setImageName($request->get('imageName'));
        $task->setUser($Utilisateur);
        //$task->setDateExp($request->get('dateExp'));

        //rename($_GET["path"], "C:\wamp64\www\worldfriendship\web\\assets\\img\\experience\\uploads\\".$_GET["imageName"]);
        rename($_GET["path"], "C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\experience\\uploads\\".$_GET["imageName"]);
        var_dump($task);
        $em->persist($task);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($task);
        return new JsonResponse($formatted);
    }


    public function PostDetailsAction(Request $request, $id, Experience $exp)
    {
        $user = $this->getUser();
        $commentaire = new Commentaire();
        $form = $this->createForm(CommentaireType::class, $commentaire)->handleRequest($request);
        $experience = $this->getDoctrine()->getRepository(Experience::class)->findIdParameter($id);
        $comm = $this->getDoctrine()->getRepository(Commentaire::class)->findCommIdParameter($id);
        //$nbr_comm = $this->getDoctrine()->getRepository('UserBundle:Commentaire')->countComments($id);


        $this->get('views_counter.views_counter')->count($exp);


        if ($user !== null) {
            $commentaire->setDateCommentaire(new \DateTime());

            if ($form->isValid()) {
                $commentaire->setUser($user);
                $em = $this->getDoctrine()->getManager();
                $commentaire->setExperience($em->getRepository('UserBundle:Experience')->find($id));
                $em->persist($commentaire);
                $em->flush();

                return $this->redirectToRoute('postDetail', array('id' => $commentaire->getExperience()));
            }
            return $this->render('@Experience/postDetail.html.twig', array(
                'form' => $form->createView(),
                'experience' => $experience,
                'comm' => $comm,
                'user' => $user
                //'nbr'=>$nbr_comm
            ));
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    public function myPostsAction($id)
    {
        $exp = $this->getDoctrine()->getManager()->getRepository
        (Experience::class)->findExpByUserParameter($id);

        return $this->render('@Experience/myPosts.html.twig',
            array('exp' => $exp,
                'id' => $id,
            )
        );
    }


    public function myPostsMobileAction($id)
    {
        $exp = $this->getDoctrine()->getManager()->getRepository
        (Experience::class)->findExpByUserParameter($id);
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);
    }

    Public function SupprimerExpAction($id, Experience $experience)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($experience->getUser() == $user) {
            $em = $this->getDoctrine()->getManager();
            $exp = $em->getRepository(Experience::class)->find($id);

            $em->remove($exp);
            $em->flush();
        } else {
            return $this->redirectToRoute("fos_user_security_login");
        }
        return $this->redirectToRoute("allPosts");
    }

    Public function SupprimerCommAction($id, Commentaire $commentaire)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($commentaire->getUser() == $user) {
            $em = $this->getDoctrine()->getManager();
            $comm = $this->getDoctrine()->getRepository(Commentaire::class)->find($id);
            $em->remove($comm);
            $em->flush();
        } else {
            return $this->redirectToRoute("fos_user_security_login");
        }
        return $this->redirectToRoute("postDetail", array('id' => $commentaire->getExperience()));
    }

    public function categorieAction(Request $request, $id)
    {
        $user = $this->getUser();
        $experience = new Experience();
        $exp = $this->getDoctrine()->getRepository('UserBundle:Experience')->findExpByCatParameter($id);
        $nom_cat = $this->getDoctrine()->getRepository('UserBundle:Categorie')->findCatByParameter($id);
        $form = $this->createForm(ExperienceType::class, $experience);
        $form = $form->handleRequest($request);


        /**
         * @var  $paginator \Knp\Component\Pager\Paginator
         */

        $paginator = $this->get('knp_paginator');
        $res = $paginator->paginate(
            $exp,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 3)
        );


        if ($user !== null) {

            if ($form->isSubmitted()) {
                $experience->setUser($user);
                $em = $this->getDoctrine()->getManager();
                $em->persist($experience);
                $em->flush();

                return $this->redirectToRoute('allPosts');
            }
            return $this->render('@Experience/categorie.html.twig', array(
                'form' => $form->createView(),
                'experience' => $res,
                'id' => $id,
                'nom_cat' => $nom_cat


            ));
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    public function rechercheAction(Request $request)
    {

    }

    public function updateAction(Request $request, $id, Experience $experience)
    {

        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($experience->getUser() == $user) {
            $exp = $this->getDoctrine()->getRepository(Experience::class)
                ->find($id);

            $form = $this->createForm(ExperienceType::class, $exp);
            $form->handleRequest($request);


            if ($user !== null) {
                //$forum->setDate(new \DateTime());
                if ($form->isSubmitted()) {
                    $exp->setUser($user);
                    $em = $this->getDoctrine()->getManager();
                    $em->flush();
                    return $this->redirectToRoute('allPosts',
                        array('id' => $exp->getCategorie()->getId()));
                }

                return $this->render('@Experience/updateExp.html.twig',
                    array("form" => $form->createView()));
            } else {
                return $this->redirectToRoute('fos_user_security_login');
            }
        }
        return $this->redirectToRoute('allPosts',
            array('id' => $experience->getCategorie()->getId()));

    }

    public function updateExpMobileAction(Request $request, $id)
    {
        $exp = $this->getDoctrine()->getRepository(Experience::class)
            ->find($id);
        $em = $this->getDoctrine()->getManager();
        $exp->setNomExp($request->get('nomExp'));
        $exp->setLieuExp($request->get('lieuExp'));
        $exp->setDescExp($request->get('descExp'));

        $em->persist($exp);
        $em->flush();

        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);

    }

    ///////// COMMENTAIRE ///////

    public function ajCommentMobileAction(Request $request)
    {


        $em = $this->getDoctrine()->getManager();
        $comment = new Commentaire();
        $question= $em->getRepository('UserBundle:Experience')->find($request->query->get("id"));
        $comment->setContenuCommentaire($request->get('contenuCommentaire'));
        $comment->setDateCommentaire(new \DateTime());

        $comment->setExperience($question);

        $em->persist($comment);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize([$comment]);
        return new JsonResponse($formatted);

    }

    public function affCommentMobileAction($id)
    {

        $comm = $this->getDoctrine()
            ->getRepository('UserBundle:Commentaire')
            ->findCommIdParameter($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($comm);
        return new JsonResponse($formatted);
    }

    //////// USER ///////

    public function newuserAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $user = new User();
        $user->setUsername($request->get('username'));
        $user->setEmail($request->get('email'));
        $x = $request->get('password');
        $t = password_hash($x, PASSWORD_DEFAULT);
        $user->setPassword($t);

        $em->persist($user);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }

    public function allaction(Request $request)
    {
        $x = $request->get('username');


        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('UserBundle:User')
            ->findby(array('username' => $x));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tasks);
        return new JsonResponse($formatted);

    }

    public function findAction(Request $request)
    {
        $x = $request->get('username');
        $password=$request->get('password');
        $em = $this->getDoctrine()->getManager();
        $users = $em->getRepository('UserBundle:User')->findBy(array('username' => $x));
        $passwordMatches = password_verify($password,$users[0]->getPassword());
        if($users == null) return new JsonResponse(null);
        else if($users!=null&&$passwordMatches==1) {
            $encoder = new JsonEncoder();
            $normalizer = new ObjectNormalizer();
            $normalizer->setCircularReferenceHandler(function ($object) {
                return $object->getId();
            });
            $serializer = new Serializer([$normalizer], [$encoder]);
            $formatted = $serializer->normalize($users);
            return new JsonResponse($formatted);
        }
        else
        {
            return new JsonResponse(null);        }
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

}