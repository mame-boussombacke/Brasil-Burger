<?php
namespace App\Controller;

use App\Entity\Livreur;
use App\Form\LivreurType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/livreur')]
class LivreurController extends AbstractController
{
    #[Route('/', name:'livreur_index')]
    public function index(EntityManagerInterface $em)
    {
        $livreurs = $em->getRepository(Livreur::class)->findAll();
        return $this->render('livreur/index.html.twig', compact('livreurs'));
    }

    #[Route('/new', name:'livreur_new')]
    public function new(Request $request, EntityManagerInterface $em)
    {
        $livreur = new Livreur();
        $form = $this->createForm(LivreurType::class, $livreur);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($livreur);
            $em->flush();
            $this->addFlash('success','Livreur ajouté !');
            return $this->redirectToRoute('livreur_index');
        }

        return $this->render('livreur/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'livreur_edit')]
    public function edit(Livreur $livreur, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(LivreurType::class, $livreur);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->flush();
            $this->addFlash('success','Livreur modifié !');
            return $this->redirectToRoute('livreur_index');
        }

        return $this->render('livreur/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'livreur_delete')]
    public function delete(Livreur $livreur, EntityManagerInterface $em)
    {
        $em->remove($livreur);
        $em->flush();
        $this->addFlash('success','Livreur supprimé !');
        return $this->redirectToRoute('livreur_index');
    }
}
