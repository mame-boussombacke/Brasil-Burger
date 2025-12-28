<?php
namespace App\Repository;

use App\Entity\Burger;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class BurgerRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Burger::class);
    }

    // Exemple: retrouver tous les burgers disponibles
    public function findAvailable(): array
    {
        return $this->createQueryBuilder('b')
                    ->andWhere('b.estDisponible = true')
                    ->getQuery()
                    ->getResult();
    }
}
