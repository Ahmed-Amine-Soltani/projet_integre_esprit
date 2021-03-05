<?php

namespace EvenementBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Form\Type\VichImageType;

class AjouterEvenement extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nomEvenement')
            ->add('typeEvenement',ChoiceType::class,[
                    'choices' =>['Associatif' => 'Associatif', 'Culturel' => 'Culturel', 'Autres' => 'Autres' ],
                    'placeholder' => 'Veuillez choisir un type d\'événement'
                ]
            )
            ->add('typeReservation',ChoiceType::class, [
                    'choices' => ['Gratuite' => 'Gratuite', 'Payante' => 'Payante'],
                    'placeholder' => 'Veuillez choisir un type de réservation',
                    'attr' => ['class' => 'res form-control']
                ]
            )
            ->add('prixEvenement')
            ->add('dateDebutEvenement',DateTimeType::class, ['data' => new \DateTime('now'),
                'widget' => 'single_text',
                'format' => 'yyyy-MM-dd HH:mm',
                'html5' => false,
                'attr' => ['class' => 'js-datepicker form-control'],
            ])
            ->add('dateFinEvenement',DateTimeType::class, ['data' => new \DateTime('now'),
                'widget' => 'single_text',
                'format' => 'yyyy-MM-dd HH:mm',
                'html5' => false,
                'attr' => ['class' => 'js-datepicker form-control'],
            ])
            ->add('lieuEvenement')
            ->add('capaciteEvenement')
            ->add('descriptionEvenement', TextareaType::class,['attr' => ['maxlength' => 255]])
            ->add('afficheFile', VichImageType::class,['label'=> false,'required'=>false])
            ->add('Ajouter',SubmitType::class, ['label' => 'Ajouter']);
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'UserBundle\Entity\Evenement'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'userbundle_evenement';
    }


}
