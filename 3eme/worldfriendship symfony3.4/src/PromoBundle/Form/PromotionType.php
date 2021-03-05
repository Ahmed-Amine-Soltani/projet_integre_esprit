<?php

namespace PromoBundle\Form;
use ProduitBundle\Entity\Produit;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\Validator\Constraints\IsNull;
use Twig\Cache\NullCache;

class PromotionType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nomPromotion')
                ->add('dateDebut')
                ->add('dateFin')
                ->add('pourcentage')

            ->add('Produit',EntityType::class,array('class'=>Produit::class,'choice_label'=>function($Produit) {
                if ($Produit->getPromotion() == 0) {
                    return $Produit->getlibelle();
                }



            }

            ,'multiple'=>false,

            ))
               ->add('ajouter',SubmitType::class);

    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'PromoBundle\Entity\Promotion'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'promobundle_promotion';
    }


}
