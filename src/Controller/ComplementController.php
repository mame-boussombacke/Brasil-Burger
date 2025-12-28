<?php
namespace App\Controller;

use App\Entity\Complement;
use App\Form\ComplementType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Cloudinary\Cloudinary;

#[Route('/complement')]
class ComplementController extends AbstractController
{
    #[Route('/', name:'complement_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $complements = $em->getRepository(Complement::class)->findAll();
        return $this->render('complement/index.html.twig', compact('complements'));
    }

    #[Route('/new', name:'complement_new')]
    public function new(Request $request, EntityManagerInterface $em, Cloudinary $cloudinary): Response
    {
        $complement = new Complement();
        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $uploaded = $cloudinary->uploadApi()->upload($imageFile->getPathname());
                $complement->setImageUrl($uploaded['secure_url']);
            }
            $em->persist($complement);
            $em->flush();
            $this->addFlash('success','Complément créé !');
            return $this->redirectToRoute('complement_index');
        }

        return $this->render('complement/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'complement_edit')]
    public function edit(Complement $complement, Request $request, EntityManagerInterface $em, Cloudinary $cloudinary)
    {
        $form = $this->createForm(ComplementType::class,$complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $uploaded = $cloudinary->uploadApi()->upload($imageFile->getPathname());
                $complement->setImageUrl($uploaded['secure_url']);
            }
            $em->flush();
            $this->addFlash('success','Complément modifié !');
            return $this->redirectToRoute('complement_index');
        }

        return $this->render('complement/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'complement_delete')]
    public function delete(Complement $complement, EntityManagerInterface $em)
    {
        $em->remove($complement);
        $em->flush();
        $this->addFlash('success','Complément supprimé !');
        return $this->redirectToRoute('complement_index');
    }
}
