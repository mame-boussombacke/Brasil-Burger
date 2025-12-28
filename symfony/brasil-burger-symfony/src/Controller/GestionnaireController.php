<?php
namespace App\Controller;

use App\Entity\Gestionnaire;
use App\Form\GestionnaireType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/gestionnaire')]
class GestionnaireController extends AbstractController
{
    #[Route('/', name:'gestionnaire_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $gestionnaires = $em->getRepository(Gestionnaire::class)->findAll();
        return $this->render('gestionnaire/index.html.twig', compact('gestionnaires'));
    }

    #[Route('/new', name:'gestionnaire_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $gestionnaire = new Gestionnaire();
        $form = $this->createForm(GestionnaireType::class, $gestionnaire);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($gestionnaire);
            $em->flush();
            $this->addFlash('success','Gestionnaire ajouté !');
            return $this->redirectToRoute('gestionnaire_index');
        }

        return $this->render('gestionnaire/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'gestionnaire_edit')]
    public function edit(Gestionnaire $gestionnaire, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(GestionnaireType::class, $gestionnaire);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->flush();
            $this->addFlash('success','Gestionnaire modifié !');
            return $this->redirectToRoute('gestionnaire_index');
        }

        return $this->render('gestionnaire/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'gestionnaire_delete')]
    public function delete(Gestionnaire $gestionnaire, EntityManagerInterface $em)
    {
        $em->remove($gestionnaire);
        $em->flush();
        $this->addFlash('success','Gestionnaire supprimé !');
        return $this->redirectToRoute('gestionnaire_index');
    }
}
