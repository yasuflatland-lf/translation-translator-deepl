# DeepL Translator for Liferay 7.4
DeepL translator for Web Content Automated translation.

## Precondition
- Blade tool is installed
- Liferay 7.4 U38+
- Java 11 installed

## How to Deploy
1. Create `Liferay Workspace` with `blade init -v 7.4`
1. Download the appropriate 7.4 bundle. In this case, let's pretend that we download the bundle and unpacked under `/Users/greeng/bundles/` as `U38` 
1. Configure `gradle.properties` as below, if it's Liferay DXP 7.4 Update 38.
   ```
   liferay.workspace.product=dxp-7.4-u38
   liferay.workspace.home.dir=/Users/greeng/bundles/U38
   ```
1. `git clone` this repository under `modules`
1. Go to the root of cloned repository folder and run `blade deploy`