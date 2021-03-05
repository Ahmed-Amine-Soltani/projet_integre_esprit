<?php

namespace AdminBundle\Controller;

use DateTime;
use ProduitBundle\Entity\SousCategorieProduit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Souscategorieproduit controller.
 *
 */
class SousCategorieProduitController extends Controller
{
    /**
     * Lists all sousCategorieProduit entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $sousCategorieProduits = $em->getRepository('ProduitBundle:SousCategorieProduit')->findAll();

        return $this->render('@Admin/souscategorieproduit/index.html.twig', array(
            'sousCategorieProduits' => $sousCategorieProduits,
        ));
    }

    /**
     * Creates a new sousCategorieProduit entity.
     *
     */
    public function newAction(Request $request)
    {
        $sousCategorieProduit = new Souscategorieproduit();
        $form = $this->createForm('ProduitBundle\Form\SousCategorieProduitType', $sousCategorieProduit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $sousCategorieProduit->setCreated( new DateTime());
            $em->persist($sousCategorieProduit);
            $em->flush();
            $this->addFlash("success", "Success");
            return $this->redirectToRoute('souscategorieproduit_index');
        }

        return $this->render('@Admin/souscategorieproduit/new.html.twig', array(
            'sousCategorieProduit' => $sousCategorieProduit,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a sousCategorieProduit entity.
     *
     */
    public function showAction(SousCategorieProduit $sousCategorieProduit)
    {
        $deleteForm = $this->createDeleteForm($sousCategorieProduit);

        return $this->render('@Admin/souscategorieproduit/show.html.twig', array(
            'sousCategorieProduit' => $sousCategorieProduit,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing sousCategorieProduit entity.
     *
     */
    public function editAction(Request $request, SousCategorieProduit $sousCategorieProduit)
    {
        $deleteForm = $this->createDeleteForm($sousCategorieProduit);
        $editForm = $this->createForm('ProduitBundle\Form\SousCategorieProduitType', $sousCategorieProduit);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $this->addFlash("success", "Success");
            return $this->redirectToRoute('souscategorieproduit_index');
        }

        return $this->render('@Admin/souscategorieproduit/edit.html.twig', array(
            'sousCategorieProduit' => $sousCategorieProduit,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }
    public function checkSousCategorieAction(Request $request)
    {
        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();
        $souscategorie = $em->getRepository('ProduitBundle:SousCategorieProduit')->find($id);

        $produit = $em->getRepository('ProduitBundle:Produit')->findOneBy(array('souscategorie'=>$souscategorie));

        if($produit){
            return new JsonResponse(true);
        }
        return new JsonResponse(false);

    }
    /**
     * Deletes a sousCategorieProduit entity.
     *
     */
    public function deleteAction(Request $request, SousCategorieProduit $sousCategorieProduit)
    {
        $form = $this->createDeleteForm($sousCategorieProduit);
        $form->handleRequest($request);

        $em = $this->getDoctrine()->getManager();
        $em->remove($sousCategorieProduit);
        $em->flush();


        return $this->redirectToRoute('souscategorieproduit_index');
    }

    public function getSousCategorieJsonAction(Request $request){

        $em = $this->getDoctrine()->getManager();

        $id_categorie = $request->request->get('categorie');
        $categorie= $em->getRepository('ProduitBundle:CategorieProduit')->find($id_categorie);

        $souscategorie= $em->getRepository('ProduitBundle:SousCategorieProduit')->findBy(array('categorie'=>$categorie));


        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(2);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);

        $data = $serializer->normalize($souscategorie);


        return new JsonResponse($data);

    }

    public function exportCsvAction(){

        $em = $this->getDoctrine()->getManager();
        $souscategories= $em->getRepository('ProduitBundle:SousCategorieProduit')->findAll();

        $writer = $this->container->get('egyg33k.csv.writer');
        $csv = $writer::createFromFileObject(new \SplTempFileObject());
        $csv->insertOne(['id', 'libelle','Categorie']);

        foreach ($souscategories as $souscategorie){
            $csv->insertOne([$souscategorie->getId(),$souscategorie->getLibelle(),$souscategorie->getCategorie()->getLibelle()]);
        }
        $csv->output('souscategorie.csv');
        die();
    }

    public function exportExcelAction(){

        $em = $this->getDoctrine()->getManager();
        $souscategories= $em->getRepository('ProduitBundle:SousCategorieProduit')->findAll();

        $phpExcelObject = $this->get('phpexcel')->createPHPExcelObject();



        $phpExcelObject->setActiveSheetIndex(0)
            ->setCellValue('A1', 'Id')
            ->setCellValue('B1', 'Libelle')
            ->setCellValue('C1', 'Categorie');

        $counter = 2;
        foreach ($souscategories as $i=>$souscategorie){

            $phpExcelObject->getActiveSheet()
                ->setCellValue('A'.$counter,$souscategorie->getId())
                ->setCellValue('B'.$counter,$souscategorie->getLibelle())
                ->setCellValue('C'.$counter,$souscategorie->getCategorie()->getLibelle());
            $counter++;

        }

        $writer = $this->get('phpexcel')->createWriter($phpExcelObject, 'Excel5');
        // create the response
        $response = $this->get('phpexcel')->createStreamedResponse($writer);
        // adding headers
        $dispositionHeader = $response->headers->makeDisposition(
            ResponseHeaderBag::DISPOSITION_ATTACHMENT,
            'souscategorie.xls'
        );

        $response->headers->set('Content-Type', 'text/vnd.ms-excel; charset=utf-8');
        $response->headers->set('Pragma', 'public');
        $response->headers->set('Cache-Control', 'maxage=1');
        $response->headers->set('Content-Disposition', $dispositionHeader);

        return $response;

    }
    /**
     * Creates a form to delete a sousCategorieProduit entity.
     *
     * @param SousCategorieProduit $sousCategorieProduit The sousCategorieProduit entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(SousCategorieProduit $sousCategorieProduit)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('souscategorieproduit_delete', array('id' => $sousCategorieProduit->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
