<?php
namespace App\Service; // S majuscule

use Cloudinary\Cloudinary;
use Cloudinary\Configuration\Configuration;
use Cloudinary\Asset\Image;

class CloudinaryService
{
    private Cloudinary $cloudinary;

    public function __construct()
    {
        $this->cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);
    }

    public function uploadImage($file): string
    {
        $result = $this->cloudinary->uploadApi()->upload($file->getPathname(), [
            'folder' => 'brasil_burger',
        ]);
        return $result['secure_url'];
    }
}
