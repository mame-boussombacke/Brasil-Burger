<?php
namespace App\Form;

use App\Entity\LigneCommande;
use App\Entity\Commande;
use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class LigneCommandeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('commande', EntityType::class, ['class'=>Commande::class, 'choice_label'=>'id'])
            ->add('burger', EntityType::class, ['class'=>Burger::class, 'choice_label'=>'nom', 'required'=>false])
            ->add('menu', EntityType::class, ['class'=>Menu::class, 'choice_label'=>'nom', 'required'=>false])
            ->add('complement', EntityType::class, ['class'=>Complement::class, 'choice_label'=>'nom', 'required'=>false])
            ->add('quantite', IntegerType::class);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(['data_class'=>LigneCommande::class]);
    }
}
