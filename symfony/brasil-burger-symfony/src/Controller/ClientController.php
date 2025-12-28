<?php
namespace App\Controller;

use App\Entity\Client;
use App\Form\ClientType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/client')]
class ClientController extends AbstractController
{
    #[Route('/', name:'client_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $clients = $em->getRepository(Client::class)->findAll();
        return $this->render('client/index.html.twig', compact('clients'));
    }

    #[Route('/new', name:'client_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $client = new Client();
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($client);
            $em->flush();
            $this->addFlash('success','Client ajouté !');
            return $this->redirectToRoute('client_index');
        }

        return $this->render('client/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'client_edit')]
    public function edit(Client $client, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->flush();
            $this->addFlash('success','Client modifié !');
            return $this->redirectToRoute('client_index');
        }

        return $this->render('client/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'client_delete')]
    public function delete(Client $client, EntityManagerInterface $em)
    {
        $em->remove($client);
        $em->flush();
        $this->addFlash('success','Client supprimé !');
        return $this->redirectToRoute('client_index');
    }
}
