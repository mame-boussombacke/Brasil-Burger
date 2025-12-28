<?php
namespace App\Form;

use App\Entity\Complement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;

class ComplementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextType::class)
            ->add('type', ChoiceType::class, ['choices'=>['Frites'=>'frites','Boisson'=>'boisson']])
            ->add('prix', MoneyType::class)
            ->add('estDisponible', CheckboxType::class,['required'=>false])
            ->add('imageFile', FileType::class, [
                'mapped'=>false,'required'=>false,
                'constraints'=>[new File(['maxSize'=>'5M','mimeTypes'=>['image/jpeg','image/png','image/webp']])]
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(['data_class'=>Complement::class]);
    }
}
