<?php
namespace App\Form;

use App\Entity\Commande;
use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommandeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('burgers', EntityType::class, [
                'class'=>Burger::class,
                'choice_label'=>'nom',
                'multiple'=>true,
                'expanded'=>true
            ])
            ->add('menus', EntityType::class, [
                'class'=>Menu::class,
                'choice_label'=>'nom',
                'multiple'=>true,
                'expanded'=>true
            ])
            ->add('complements', EntityType::class, [
                'class'=>Complement::class,
                'choice_label'=>'nom',
                'multiple'=>true,
                'expanded'=>true
            ])
            ->add('typeLivraison', ChoiceType::class, [
                'choices'=>['Sur place'=>'Sur place','À emporter'=>'À emporter','Livraison'=>'Livraison']
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(['data_class'=>Commande::class]);
    }
}
