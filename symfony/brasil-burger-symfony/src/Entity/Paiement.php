<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Entity\Commande;

#[ORM\Entity]
#[ORM\Table(name:"Paiements")]
class Paiement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Commande::class)]
    #[ORM\JoinColumn(nullable: false)]
    private Commande $commande;

    #[ORM\Column(type:"float")]
    private float $montant;

    #[ORM\Column(type:"datetime")]
    private \DateTimeInterface $date;

    #[ORM\Column(type:"string", length:20)]
    private string $moyen; // Wave, OM

    public function __construct() { $this->date = new \DateTime(); }

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getCommande(): Commande { return $this->commande; }
    public function setCommande(Commande $commande): self { $this->commande = $commande; return $this; }
    public function getMontant(): float { return $this->montant; }
    public function setMontant(float $montant): self { $this->montant = $montant; return $this; }
    public function getDate(): \DateTimeInterface { return $this->date; }
    public function getMoyen(): string { return $this->moyen; }
    public function setMoyen(string $moyen): self { $this->moyen = $moyen; return $this; }
}
