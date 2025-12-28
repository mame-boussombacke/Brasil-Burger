<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Entity\Zone;

#[ORM\Entity]
#[ORM\Table(name:"Quartiers")]
class Quartier
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length:100)]
    private string $nom;

    #[ORM\ManyToOne(targetEntity:Zone::class)]
    private Zone $zone;

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getNom(): string { return $this->nom; }
    public function setNom(string $nom): self { $this->nom = $nom; return $this; }
    public function getZone(): Zone { return $this->zone; }
    public function setZone(Zone $zone): self { $this->zone = $zone; return $this; }
}
