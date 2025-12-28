<?php

namespace App\Controller;

use App\Entity\Burger;
use App\Form\BurgerType;
use App\Repository\BurgerRepository;
use Cloudinary\Cloudinary;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/gestionnaire/burgers')]
class BurgerController extends AbstractController
{
    #[Route('/', name: 'burger_index')]
    public function index(BurgerRepository $burgerRepository): Response
    {
        return $this->render('burger/index.html.twig', [
            'burgers' => $burgerRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'burger_new')]
    public function new(
        Request $request,
        EntityManagerInterface $em,
        Cloudinary $cloudinary
    ): Response {
        $burger = new Burger();
        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $imageFile = $form->get('image')->getData();

            if ($imageFile !== null) {
                // Upload Cloudinary
                $upload = $cloudinary->uploadApi()->upload(
                    $imageFile->getPathname(),
                    [
                        'folder' => 'brasil-burger/burgers',
                        'resource_type' => 'image'
                    ]
                );

                $burger->setImageUrl($upload['secure_url']);
            }

            $em->persist($burger);
            $em->flush();

            $this->addFlash('success', 'Burger ajouté avec succès');

            return $this->redirectToRoute('burger_index');
        }

        return $this->render('burger/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }
}
