<?php
namespace App\Controller;

use App\Entity\Quartier;
use App\Entity\Zone;
use App\Form\QuartierType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/quartier')]
class QuartierController extends AbstractController
{
    #[Route('/', name:'quartier_index')]
    public function index(EntityManagerInterface $em)
    {
        $quartiers = $em->getRepository(Quartier::class)->findAll();
        return $this->render('quartier/index.html.twig', compact('quartiers'));
    }

    #[Route('/new', name:'quartier_new')]
    public function new(Request $request, EntityManagerInterface $em)
    {
        $quartier = new Quartier();
        $form = $this->createForm(QuartierType::class, $quartier);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($quartier);
            $em->flush();
            $this->addFlash('success','Quartier ajouté !');
            return $this->redirectToRoute('quartier_index');
        }

        return $this->render('quartier/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'quartier_edit')]
    public function edit(Quartier $quartier, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(QuartierType::class, $quartier);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->flush();
            $this->addFlash('success','Quartier modifié !');
            return $this->redirectToRoute('quartier_index');
        }

        return $this->render('quartier/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'quartier_delete')]
    public function delete(Quartier $quartier, EntityManagerInterface $em)
    {
        $em->remove($quartier);
        $em->flush();
        $this->addFlash('success','Quartier supprimé !');
        return $this->redirectToRoute('quartier_index');
    }
}
