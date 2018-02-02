/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CvthequeTestModule } from '../../../test.module';
import { ShareProfilDetailComponent } from '../../../../../../main/webapp/app/entities/share-profil/share-profil-detail.component';
import { ShareProfilService } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.service';
import { ShareProfil } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.model';

describe('Component Tests', () => {

    describe('ShareProfil Management Detail Component', () => {
        let comp: ShareProfilDetailComponent;
        let fixture: ComponentFixture<ShareProfilDetailComponent>;
        let service: ShareProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [ShareProfilDetailComponent],
                providers: [
                    ShareProfilService
                ]
            })
            .overrideTemplate(ShareProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShareProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShareProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ShareProfil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shareProfil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
