<?php

namespace ProduitBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\ColorType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ProduitType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('libelle')
                ->add('description',TextareaType::class)
                ->add('prix',IntegerType::class)
                ->add('stock')
                ->add('taille',ChoiceType::class,
                    array(
                        'choices' => array(
                            'S' => 's',
                            'M' => 'm',
                            'XL' => 'xl',
                            'L' => 'l',
                        ),))
                ->add('color',ColorType::class)
                ->add('imagefileFirst',FileType::class)
                ->add('imagefileSecond',FileType::class)
                ->add('imagefileThird',FileType::class);

    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(['required' => false,]);
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'produitbundle_produit';
    }


}
