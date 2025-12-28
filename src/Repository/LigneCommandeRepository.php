<?php

namespace App\Repository;

use App\Entity\LigneCommande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class LigneCommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, LigneCommande::class);
    }

    // ✅ Burgers les plus vendus du jour
    public function burgersPlusVendusDuJour(): array
    {
        $today = new \DateTime('today');

        return $this->createQueryBuilder('lc')
            ->select('b.nom AS nom, SUM(lc.quantite) AS totalVendu')
            ->leftJoin('lc.burger', 'b')
            ->join('lc.commande', 'c')
            ->andWhere('b.id IS NOT NULL')
            ->andWhere('c.dateCommande >= :today')
            ->setParameter('today', $today)
            ->groupBy('b.id, b.nom')
            ->orderBy('totalVendu', 'DESC')
            ->setMaxResults(5)
            ->getQuery()
            ->getResult();
    }

    // ✅ Menus les plus vendus du jour
    public function menusPlusVendusDuJour(): array
    {
        $today = new \DateTime('today');

        return $this->createQueryBuilder('lc')
            ->select('m.nom AS nom, SUM(lc.quantite) AS totalVendu')
            ->leftJoin('lc.menu', 'm')
            ->join('lc.commande', 'c')
            ->andWhere('m.id IS NOT NULL')
            ->andWhere('c.dateCommande >= :today')
            ->setParameter('today', $today)
            ->groupBy('m.id, m.nom')
            ->orderBy('totalVendu', 'DESC')
            ->setMaxResults(5)
            ->getQuery()
            ->getResult();
    }

    // ✅ Lignes par client
    public function findByClient($clientId): array
    {
        return $this->createQueryBuilder('lc')
            ->join('lc.commande', 'c')
            ->andWhere('c.client = :clientId')
            ->setParameter('clientId', $clientId)
            ->getQuery()
            ->getResult();
    }

    // ✅ Lignes par date
    public function findByDate(\DateTime $date): array
    {
        return $this->createQueryBuilder('lc')
            ->join('lc.commande', 'c')
            ->andWhere('c.dateCommande BETWEEN :start AND :end')
            ->setParameter('start', $date->format('Y-m-d 00:00:00'))
            ->setParameter('end', $date->format('Y-m-d 23:59:59'))
            ->getQuery()
            ->getResult();
    }

    // ✅ Lignes par état
    public function findByEtat(string $etat): array
    {
        return $this->createQueryBuilder('lc')
            ->join('lc.commande', 'c')
            ->andWhere('c.etat = :etat')
            ->setParameter('etat', $etat)
            ->getQuery()
            ->getResult();
    }
}
