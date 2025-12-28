<?php
namespace App\Controller;

use App\Entity\Menu;
use App\Form\MenuType;
use App\Service\CloudinaryService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/menu')]
class MenuController extends AbstractController
{
    #[Route('/', name: 'menu_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $menus = $em->getRepository(Menu::class)->findAll();

        return $this->render('menu/index.html.twig', [
            'menus' => $menus
        ]);
    }

    #[Route('/new', name: 'menu_new')]
    public function new(Request $request, EntityManagerInterface $em, CloudinaryService $cloudinaryService): Response
    {
        $menu = new Menu();
        $form = $this->createForm(MenuType::class, $menu);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $menu->setImageUrl($cloudinaryService->uploadImage($imageFile));
            }

            $em->persist($menu);
            $em->flush();

            $this->addFlash('success', 'Menu créé avec succès');
            return $this->redirectToRoute('menu_index');
        }

        return $this->render('menu/new.html.twig', [
            'form' => $form->createView()
        ]);
    }

    #[Route('/edit/{id}', name: 'menu_edit')]
    public function edit(Menu $menu, Request $request, EntityManagerInterface $em, CloudinaryService $cloudinaryService): Response
    {
        $form = $this->createForm(MenuType::class, $menu);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $menu->setImageUrl($cloudinaryService->uploadImage($imageFile));
            }

            $em->flush();

            $this->addFlash('success', 'Menu modifié avec succès');
            return $this->redirectToRoute('menu_index');
        }

        return $this->render('menu/new.html.twig', [
            'form' => $form->createView(),
            'menu' => $menu
        ]);
    }

    #[Route('/delete/{id}', name: 'menu_delete')]
    public function delete(Menu $menu, EntityManagerInterface $em): Response
    {
        $em->remove($menu);
        $em->flush();

        $this->addFlash('success', 'Menu supprimé');
        return $this->redirectToRoute('menu_index');
    }
}
