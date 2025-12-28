<?php
namespace App\Repository;

use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    //  Commandes en cours de la journée
    public function commandesEnCoursDuJour(): array
    {
        $today = new \DateTime('today');
        return $this->createQueryBuilder('c')
                    ->andWhere('c.dateCommande >= :today')
                    ->andWhere('c.etat = :etat')
                    ->setParameter('today', $today)
                    ->setParameter('etat', 'EN_COURS')
                    ->getQuery()
                    ->getResult();
    }

    //  Commandes validées de la journée
    public function commandesValideesDuJour(): int
{
    $today = new \DateTime('today');

    return (int) $this->createQueryBuilder('c')
        ->select('COUNT(c.id)')
        ->where('c.dateCommande >= :today')
        ->andWhere('c.etat = :etat')
        ->setParameter('today', $today)
        ->setParameter('etat', 'VALIDÉE')
        ->getQuery()
        ->getSingleScalarResult();
}


    //  Recettes journalières
    public function recettesDuJour(): float
{
    $today = new \DateTime('today');

    return (float) $this->createQueryBuilder('c')
        ->select('SUM(c.total)')
        ->where('c.dateCommande >= :today')
        ->andWhere('c.paye = true')
        ->setParameter('today', $today)
        ->getQuery()
        ->getSingleScalarResult();
}
    //  Commandes annulées du jour
    public function commandesAnnuleesDuJour(): array
    {
        $today = new \DateTime('today');
        return $this->createQueryBuilder('c')
                    ->andWhere('c.dateCommande >= :today')
                    ->andWhere('c.etat = :etat')
                    ->setParameter('today', $today)
                    ->setParameter('etat', 'ANNULÉE')
                    ->getQuery()
                    ->getResult();
    }
    /**
 * Commandes filtrées par client
 */
public function findByClient($clientId): array
{
    return $this->createQueryBuilder('c')
        ->andWhere('c.client = :clientId')
        ->setParameter('clientId', $clientId)
        ->getQuery()
        ->getResult();
}

/**
 * Commandes filtrées par burger
 */
public function findByBurger($burgerId): array
{
    return $this->createQueryBuilder('c')
        ->join('c.lignes', 'lc')
        ->andWhere('lc.burger = :burgerId')
        ->setParameter('burgerId', $burgerId)
        ->getQuery()
        ->getResult();
}

/**
 * Commandes filtrées par menu
 */
public function findByMenu($menuId): array
{
    return $this->createQueryBuilder('c')
        ->join('c.lignes', 'lc')
        ->andWhere('lc.menu = :menuId')
        ->setParameter('menuId', $menuId)
        ->getQuery()
        ->getResult();
}
}
