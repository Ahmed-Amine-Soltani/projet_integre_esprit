<?php

namespace AdminBundle\Controller;

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
class CategorieProduitController extends Controller
{
    /**
     * Lists all categorieProduit entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $categorieProduits = $em->getRepository('ProduitBundle:CategorieProduit')->findAll();

        return $this->render('@Admin/categorieproduit/index.html.twig', array(
            'categorieProduits' => $categorieProduits,
        ));
    }

    /**
     * Creates a new categorieProduit entity.
     *
     */
    public function newAction(Request $request)
    {
        $categorieProduit = new Categorieproduit();

        $form = $this->createForm('ProduitBundle\Form\CategorieProduitType', $categorieProduit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $em = $this->getDoctrine()->getManager();

            $categorieProduit->setCreated( new DateTime());
            $em->persist($categorieProduit);
            $em->flush();
            $this->addFlash("success", "Success");
            return $this->redirectToRoute('categorieproduit_index');

        }

        return $this->render('@Admin/categorieproduit/new.html.twig', array(
            'categorieproduit' => $categorieProduit,
            'form' => $form->createView(),
        ));




    }

    /**
     * Finds and displays a categorieProduit entity.
     *
     */
    public function showAction(CategorieProduit $categorieProduit)
    {
        $deleteForm = $this->createDeleteForm($categorieProduit);

        return $this->render('@Admin/categorieproduit/show.html.twig', array(
            'categorieProduit' => $categorieProduit,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing categorieProduit entity.
     *
     */
    public function editAction(Request $request, CategorieProduit $categorieProduit)
    {
        $deleteForm = $this->createDeleteForm($categorieProduit);
        $editForm = $this->createForm('ProduitBundle\Form\CategorieProduitType', $categorieProduit);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {


            $this->getDoctrine()->getManager()->flush();
            $this->addFlash("success", "Success");
            return $this->redirectToRoute('categorieproduit_index');
        }

        return $this->render('@Admin/categorieproduit/edit.html.twig', array(
            'categorieProduit' => $categorieProduit,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    public function checkCategorieAction(Request $request)
    {
        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();
        $categorie = $em->getRepository('ProduitBundle:CategorieProduit')->find($id);

        $souscategorie = $em->getRepository('ProduitBundle:SousCategorieProduit')->findOneBy(array('categorie'=>$categorie));

        if($souscategorie){
            return new JsonResponse(true);
        }
        return new JsonResponse(false);

    }



    /**
     * Deletes a categorieProduit entity.
     *
     */
    public function deleteAction(Request $request, CategorieProduit $categorieProduit)
    {
        $form = $this->createDeleteForm($categorieProduit);
        $form->handleRequest($request);

        $em = $this->getDoctrine()->getManager();
        $em->remove($categorieProduit);
        $em->flush();


        return $this->redirectToRoute('categorieproduit_index');
    }


    public function getCategorieJsonAction(){

        $em = $this->getDoctrine()->getManager();

        $categorie= $em->getRepository('ProduitBundle:CategorieProduit')->findAll();

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

    public function exportCsvAction(){

        $em = $this->getDoctrine()->getManager();
        $categories= $em->getRepository('ProduitBundle:CategorieProduit')->findAll();

        $writer = $this->container->get('egyg33k.csv.writer');
        $csv = $writer::createFromFileObject(new \SplTempFileObject());
        $csv->insertOne(['id', 'libelle']);

        foreach ($categories as $categorie){
            $csv->insertOne([$categorie->getId(),$categorie->getLibelle()]);
        }
        $csv->output('categorie.csv');
        die();
    }

    public function exportExcelAction(){

        $em = $this->getDoctrine()->getManager();
        $categories= $em->getRepository('ProduitBundle:CategorieProduit')->findAll();

        $phpExcelObject = $this->get('phpexcel')->createPHPExcelObject();



        $phpExcelObject->setActiveSheetIndex(0)
            ->setCellValue('A1', 'Id')
            ->setCellValue('B1', 'Libelle');

        $counter = 2;
        foreach ($categories as $i=>$categorie){

            $phpExcelObject->getActiveSheet()
            ->setCellValue('A'.$counter,$categorie->getId())
            ->setCellValue('B'.$counter,$categorie->getLibelle());
            $counter++;

        }

        $writer = $this->get('phpexcel')->createWriter($phpExcelObject, 'Excel5');
        // create the response
        $response = $this->get('phpexcel')->createStreamedResponse($writer);
        // adding headers
        $dispositionHeader = $response->headers->makeDisposition(
            ResponseHeaderBag::DISPOSITION_ATTACHMENT,
            'categorie.xls'
        );

        $response->headers->set('Content-Type', 'text/vnd.ms-excel; charset=utf-8');
        $response->headers->set('Pragma', 'public');
        $response->headers->set('Cache-Control', 'maxage=1');
        $response->headers->set('Content-Disposition', $dispositionHeader);

        return $response;

    }
    /**
     * Creates a form to delete a categorieProduit entity.
     *
     * @param CategorieProduit $categorieProduit The categorieProduit entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(CategorieProduit $categorieProduit)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('categorieproduit_delete', array('id' => $categorieProduit->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
