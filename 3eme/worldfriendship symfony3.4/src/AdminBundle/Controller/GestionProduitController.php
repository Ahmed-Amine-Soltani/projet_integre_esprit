<?php

namespace AdminBundle\Controller;

use DateTime;
use ProduitBundle\Entity\Produit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;

/**
 * Produit controller.
 *
 */
class GestionProduitController extends Controller
{

    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $produits = $em->getRepository('ProduitBundle:Produit')->findAll();

        return $this->render('@Admin/gestionproduit/index.html.twig', array(
            'produits' => $produits,
        ));
    }

    /**
     * Creates a new gestionproduit entity.
     *
     */
    public function newAction(Request $request)
    {
        $produit = new Produit();
        $form = $this->createForm('ProduitBundle\Form\ProduitType', $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $em = $this->getDoctrine()->getManager();

            $id_souscategorie =  $request->request->get('souscategorie');
            $souscategorie= $em->getRepository('ProduitBundle:SousCategorieProduit')->find($id_souscategorie);
            $produit->setSouscategorie($souscategorie);
            $produit->setPromotion(0);
            $produit->setCreated( new DateTime());


            $em->persist($produit);
            $em->flush();

            $this->addFlash("success", "Success");

            return $this->redirectToRoute('produit_index');
        }

        return $this->render('@Admin/gestionproduit/new.html.twig', array(
            'gestionproduit' => $produit,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a gestionproduit entity.
     *
     */
    public function showAction(Produit $produit)
    {
        $deleteForm = $this->createDeleteForm($produit);

        return $this->render('@Admin/gestionproduit/show.html.twig', array(
            'gestionproduit' => $produit,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing gestionproduit entity.
     *
     */
    public function editAction(Request $request, Produit $produit)
    {
        $deleteForm = $this->createDeleteForm($produit);
        $editForm = $this->createForm('ProduitBundle\Form\ProduitType', $produit);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();

            $id_souscategorie =  $request->request->get('souscategorie');
            $souscategorie= $em->getRepository('ProduitBundle:SousCategorieProduit')->find($id_souscategorie);
            $produit->setSouscategorie($souscategorie);

            $em->flush();

            $this->addFlash("success", "Success");

            return $this->redirectToRoute('produit_index');
        }

        return $this->render('@Admin/gestionproduit/edit.html.twig', array(
            'gestionproduit' => $produit,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    public function checkProduitPanierAction(Request $request){
        $id = $request->request->get('id');
        $em = $this->getDoctrine()->getManager();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($id);

        $lignepanier = $em->getRepository('ProduitBundle:lignedepanier')->findOneBy(array('produit'=>$produit));

        if($lignepanier){
            return new JsonResponse(true);
        }

        return new JsonResponse(false);


    }
    /**
     * Deletes a gestionproduit entity.
     *
     */
    public function deleteAction(Request $request, Produit $produit)
    {

        $form = $this->createDeleteForm($produit);
        $form->handleRequest($request);

        $em = $this->getDoctrine()->getManager();
        $em->remove($produit);
        $em->flush();

        return $this->redirectToRoute('produit_index');
    }

    public function exportCsvAction(){

        $em = $this->getDoctrine()->getManager();
        $produits= $em->getRepository('ProduitBundle:Produit')->findAll();

        $writer = $this->container->get('egyg33k.csv.writer');
        $csv = $writer::createFromFileObject(new \SplTempFileObject());
        $csv->insertOne(['id', 'libelle','modele','prix','stock','taille','color']);

        foreach ($produits as $produit){
            $csv->insertOne([$produit->getId(),$produit->getLibelle(),$produit->getSouscategorie()->getLibelle(),
                $produit->getPrix(),$produit->getStock(),$produit->getTaille(),$produit->getColor()]);
        }
        $csv->output('produit.csv');
        die();
    }

    public function exportExcelAction(){

        $em = $this->getDoctrine()->getManager();
        $produits= $em->getRepository('ProduitBundle:Produit')->findAll();

        $phpExcelObject = $this->get('phpexcel')->createPHPExcelObject();



        $phpExcelObject->setActiveSheetIndex(0)
            ->setCellValue('A1', 'Id')
            ->setCellValue('B1', 'Libelle')
            ->setCellValue('C1', 'modele')
            ->setCellValue('D1', 'prix')
            ->setCellValue('E1', 'stock')
            ->setCellValue('F1', 'taille')
            ->setCellValue('G1', 'color')
            ->setCellValue('H1', 'Created');

        $counter = 2;
        foreach ($produits as $i=>$produit){

            $phpExcelObject->getActiveSheet()
                ->setCellValue('A'.$counter,$produit->getId())
                ->setCellValue('B'.$counter,$produit->getLibelle())
                ->setCellValue('C'.$counter,$produit->getSouscategorie()->getLibelle())
                ->setCellValue('D'.$counter,$produit->getPrix())
                ->setCellValue('E'.$counter,$produit->getStock())
                ->setCellValue('F'.$counter,$produit->getTaille())
                ->setCellValue('G'.$counter,$produit->getColor())
                ->setCellValue('H'.$counter,$produit->getCreated());
            $counter++;

        }

        $writer = $this->get('phpexcel')->createWriter($phpExcelObject, 'Excel5');
        // create the response
        $response = $this->get('phpexcel')->createStreamedResponse($writer);
        // adding headers
        $dispositionHeader = $response->headers->makeDisposition(
            ResponseHeaderBag::DISPOSITION_ATTACHMENT,
            'produit.xls'
        );

        $response->headers->set('Content-Type', 'text/vnd.ms-excel; charset=utf-8');
        $response->headers->set('Pragma', 'public');
        $response->headers->set('Cache-Control', 'maxage=1');
        $response->headers->set('Content-Disposition', $dispositionHeader);

        return $response;

    }

    /**
     * Creates a form to delete a gestionproduit entity.
     *
     * @param Produit $produit The gestionproduit entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Produit $produit)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('produit_delete', array('id' => $produit->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
