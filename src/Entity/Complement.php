<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name:"Complements")]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(name:"Nom", type:"string", length:255)]
    private string $nom;

    #[ORM\Column(name:"Type", type:"string", length:50)]
    private string $type;

    #[ORM\Column(name:"Prix", type:"float")]
    private float $prix;

    #[ORM\Column(name:"ImageUrl", type:"string", length:255, nullable:true)]
    private ?string $imageUrl = null;

    #[ORM\Column(name:"EstDisponible", type:"boolean")]
    private bool $estDisponible = true;

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getNom(): string { return $this->nom; }
    public function setNom(string $nom): self { $this->nom = $nom; return $this; }
    public function getType(): string { return $this->type; }
    public function setType(string $type): self { $this->type = $type; return $this; }
    public function getPrix(): float { return $this->prix; }
    public function setPrix(float $prix): self { $this->prix = $prix; return $this; }
    public function getImageUrl(): ?string { return $this->imageUrl; }
    public function setImageUrl(?string $imageUrl): self { $this->imageUrl = $imageUrl; return $this; }
    public function getEstDisponible(): bool { return $this->estDisponible; }
    public function setEstDisponible(bool $estDisponible): self { $this->estDisponible = $estDisponible; return $this; }
}
